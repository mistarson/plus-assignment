package plus.plusassignment.api.user.dto;

import jakarta.validation.constraints.NotBlank;

public record NormalUserLoginDTO(
        @NotBlank String email,
        @NotBlank String password
) {

}
