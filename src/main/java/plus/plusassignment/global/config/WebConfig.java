package plus.plusassignment.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import plus.plusassignment.global.interceptor.AuthenticationInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;

    private final String[] WHITE_LIST = {
            "/api/normal-users/signup", "/api/normal-users/login",
            "/api/normal-users/duplication-username", "/api/normal-users/duplication-email",

            "/api/social-users/login", "/api/social-users/duplication-email",

            "/api/emails/vertification-requests"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(WHITE_LIST);
    }
}
