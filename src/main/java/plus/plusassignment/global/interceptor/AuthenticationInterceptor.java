package plus.plusassignment.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import plus.plusassignment.global.jwt.JwtManager;
import plus.plusassignment.global.jwt.TokenUtils;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtManager jwtManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        String token = TokenUtils.getTokenFromRequest(request);

        jwtManager.validateToken(token);

        return true;
    }
}
