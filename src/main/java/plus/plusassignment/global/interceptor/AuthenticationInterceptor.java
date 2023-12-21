package plus.plusassignment.global.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import plus.plusassignment.global.exception.common.ErrorCode;
import plus.plusassignment.global.exception.jwt.JwtInvalidException;
import plus.plusassignment.global.jwt.JwtManager;
import plus.plusassignment.global.jwt.TokenUtils;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) {

        String token = TokenUtils.getTokenFromRequest(request);

        try {
            jwtManager.validateToken(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtInvalidException(ErrorCode.INVALID_JWT_SIGNATURE_EXCEPTION, e);
        } catch (ExpiredJwtException e) {
            throw new JwtInvalidException(ErrorCode.EXPIRED_JWT_TOKEN_EXCEPTION, e);
        } catch (UnsupportedJwtException e) {
            throw new JwtInvalidException(ErrorCode.UNSUPPORTED_JWT_TOKEN_EXCEPTION, e);
        } catch (IllegalArgumentException e) {
            throw new JwtInvalidException(ErrorCode.INVALID_JWT_EXCEPTION, e);
        }

        return true;
    }
}
