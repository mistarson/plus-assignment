package plus.plusassignment.api.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;

public class PasswordNotContainUsernameValidator implements
        ConstraintValidator<PasswordNotContainUsername, NormalUserRegisterDTO.Request> {

    @Override
    public boolean isValid(NormalUserRegisterDTO.Request requestDTO, ConstraintValidatorContext context) {

        String username = requestDTO.username();
        String password = requestDTO.password();

        if (!StringUtils.hasText(password) || !StringUtils.hasText(username)) {
            return false;
        }

        return !password.contains(username);
    }
}
