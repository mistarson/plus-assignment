package plus.plusassignment.api.user.dto;

import plus.plusassignment.domain.user.constant.SocialType;
import plus.plusassignment.domain.user.entity.SocialUser;

public record SocialUserInfo(
        String username,
        String email,
        SocialType socialType
) {

    public SocialUser toEntity() {
        return SocialUser.builder()
                .username(username)
                .email(email)
                .socialType(socialType)
                .build();
    }

}
