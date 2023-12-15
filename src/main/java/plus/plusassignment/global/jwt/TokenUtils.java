package plus.plusassignment.global.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class TokenUtils {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static String getTokenFromRequest(HttpServletRequest req) {

        String authorizationHeader = req.getHeader(AUTHORIZATION_HEADER);

        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(
                BEARER_PREFIX)) {
            throw new IllegalArgumentException("인증 헤더가 비어있거나 잘못된 권한 부여 유형입니다.");
        }

        return authorizationHeader.substring(BEARER_PREFIX.length());
    }
}
