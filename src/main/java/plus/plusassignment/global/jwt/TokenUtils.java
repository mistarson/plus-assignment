package plus.plusassignment.global.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import plus.plusassignment.global.exception.jwt.NoJwtException;
import plus.plusassignment.global.exception.jwt.UnsupportedGrantTypeException;

public class TokenUtils {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getTokenFromRequest(HttpServletRequest req) {

        String authorizationHeader = req.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(authorizationHeader)) {
            throw new NoJwtException();
        }

        if (!authorizationHeader.startsWith(BEARER_PREFIX)) {
            throw new UnsupportedGrantTypeException();
        }

        return authorizationHeader.substring(BEARER_PREFIX.length());
    }
}
