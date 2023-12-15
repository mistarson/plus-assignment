package plus.plusassignment.api.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import plus.plusassignment.domain.user.constant.SocialType;

public class PossibleSocialTypeValidator implements
        ConstraintValidator<PossibleSocialType, String> {

    @Override
    public boolean isValid(String socialType, ConstraintValidatorContext context) {

        String oauthServiceBeanName = SocialType.getOauthServiceBeanName(socialType);

        return StringUtils.hasText(oauthServiceBeanName);
    }
}
