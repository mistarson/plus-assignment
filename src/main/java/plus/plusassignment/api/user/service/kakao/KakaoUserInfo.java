package plus.plusassignment.api.user.service.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import plus.plusassignment.api.user.dto.SocialUserInfo;
import plus.plusassignment.domain.user.constant.SocialType;

public record KakaoUserInfo(
        Long id,
        LocalDateTime connected_at,
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {

    public record KakaoAccount(
            Profile profile,
            Boolean has_email,
            Boolean email_needs_agreement,
            Boolean is_email_valid,
            Boolean is_email_verified,
            String email
    ) {}

    public record Profile(String nickname) {}

    public SocialUserInfo toSocialUserInfo() {
        return new SocialUserInfo(kakaoAccount.profile.nickname, kakaoAccount.email,
                SocialType.KAKAO);
    }

}
