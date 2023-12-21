package plus.plusassignment.global.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import plus.plusassignment.global.argumentresolver.UserIdArgumentResolver;
import plus.plusassignment.global.interceptor.AuthenticationInterceptor;
import plus.plusassignment.global.jwt.JwtManager;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtManager jwtManager;

    private final String[] WHITE_LIST = {
            "/api/normal-users/signup", "/api/normal-users/login",
            "/api/normal-users/duplication-username", "/api/normal-users/duplication-email",

            "/api/social-users/login", "/api/social-users/duplication-email",

            "/api/emails/vertification-requests"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(jwtManager))
                .addPathPatterns("/api/**")
                .excludePathPatterns(WHITE_LIST);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserIdArgumentResolver(jwtManager));
    }
}
