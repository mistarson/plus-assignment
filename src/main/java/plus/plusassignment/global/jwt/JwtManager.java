package plus.plusassignment.global.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtManager {

    private final long accessTime;
    private final long refreshTime;
    private final Key key;
    private final String issuer;

    public JwtManager(@Value("${jwt.token.issuer}") String issuer,
            @Value("${jwt.token.secret}") String secret,
            @Value("${jwt.token.access-time}") long accessTime,
            @Value("${jwt.token.refresh-time}") long refreshTime) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.issuer = issuer;
        this.accessTime = accessTime;
        this.refreshTime = refreshTime;
    }

    public String createAccessToken(String userName) {
        long nowTime = new Date().getTime();
        Date issuedAt = new Date();
        Date expiration = new Date(nowTime + accessTime);

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userName)
                .setAudience(TokenType.ACCESS.toString())
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String userName) {
        long nowTime = new Date().getTime();
        Date issuedAt = new Date();
        Date expiration = new Date(nowTime + refreshTime);

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(userName)
                .setAudience(TokenType.REFRESH.toString())
                .setExpiration(expiration)
                .setIssuedAt(issuedAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public TokenLoginDTO createAccessAndRefreshToken(String username) {

        String accessToken = createAccessToken(username);
        String refreshToken = createRefreshToken(username);

        return new TokenLoginDTO(accessToken, refreshToken);
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
                .getSubject();
    }

    public String getTokenTypeFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody()
                .getAudience();
    }

    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
}
