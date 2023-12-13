package plus.plusassignment.api.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;

public class PasswordMatchConfirmPasswordValidator implements
        ConstraintValidator<PasswordMatchConfirmPassword, NormalUserRegisterDTO.Request> {

    @Override
    public boolean isValid(NormalUserRegisterDTO.Request requestDTO, ConstraintValidatorContext context) {

        String password = requestDTO.password();
        String confirmPassword = requestDTO.confirmPassword();

        if (!StringUtils.hasText(password) || !StringUtils.hasText(confirmPassword)) {
            return false;
        }

        return password.equals(confirmPassword);
    }
}
