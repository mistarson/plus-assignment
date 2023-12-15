package plus.plusassignment.api.user.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.SocialLoginDTO;
import plus.plusassignment.api.user.dto.SocialUserInfo;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.domain.user.service.SocialUserService;

@Service
@RequiredArgsConstructor
public class ApiSocialUserLoginService {

    private final SocialUserService socialUserService;

    @Transactional
    public SocialLoginDTO.Response socialLogin(String accessToken, String socialType) {

        SocialUserInfo socialUserInfo = getSocialUserInfo(accessToken, socialType);

        Optional<SocialUser> optionalSocialUser = socialUserService.findByEmail(
                socialUserInfo.email());

        if (optionalSocialUser.isPresent()) {
            return SocialLoginDTO.Response.from(optionalSocialUser.get());
        }
        SocialUser socialUser = socialUserService.registerSocialUser(
                socialUserInfo.toEntity());

        return SocialLoginDTO.Response.from(socialUser);
    }

    private SocialUserInfo getSocialUserInfo(String accessToken, String socialType) {

        OauthLoginService oauthLoginService =
                OauthLoginServiceFactory.getOauthLoginService(socialType);

        return oauthLoginService.getUserInfo(accessToken);
    }

}
