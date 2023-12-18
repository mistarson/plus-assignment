package plus.plusassignment.api.user.dto;

import plus.plusassignment.api.user.validator.PossibleSocialType;

public class SocialLoginDTO {

    public record Request(
            @PossibleSocialType String socialType
    ) {}

}
