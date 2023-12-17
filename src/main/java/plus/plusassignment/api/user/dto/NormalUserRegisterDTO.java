package plus.plusassignment.api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.crypto.password.PasswordEncoder;
import plus.plusassignment.api.user.validator.PasswordMatchConfirmPassword;
import plus.plusassignment.api.user.validator.PasswordNotContainUsername;
import plus.plusassignment.domain.user.entity.NormalUser;

public class NormalUserRegisterDTO {

    @PasswordNotContainUsername
    @PasswordMatchConfirmPassword
    public record Request(
            @Email String email,
            @Pattern(regexp = "^[a-z0-9]{3,20}$") String username,
            @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*?_]{4,15}$") String password,
            @NotBlank String confirmPassword
    ) {

        public NormalUser toEntity(PasswordEncoder passwordEncoder) {
            return NormalUser.builder()
                    .username(username)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .build();
        }

    }

    public record Response(
            String id,
            String email,
            String username
    ) {

        public static Response from(NormalUser normalUser) {
            return new Response(normalUser.getId(), normalUser.getEmail(),
                    normalUser.getUsername());
        }

    }
}
