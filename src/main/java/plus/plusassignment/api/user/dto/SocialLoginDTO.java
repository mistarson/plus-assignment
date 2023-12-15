package plus.plusassignment.api.user.dto;

import plus.plusassignment.api.user.validator.PossibleSocialType;
import plus.plusassignment.domain.user.entity.SocialUser;

public class SocialLoginDTO {

    public record Request(
            @PossibleSocialType String socialType
    ) {}

    public record Response(
            String id,
            String username,
            String email
    ) {
        public static Response from(SocialUser socialUser) {
            return new Response(socialUser.getId(), socialUser.getUsername(), socialUser.getEmail());
        }
    }
}
