package plus.plusassignment.api.user.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = PossibleSocialTypeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PossibleSocialType {

    String message() default "지원하지 않는 소셜 로그인 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
