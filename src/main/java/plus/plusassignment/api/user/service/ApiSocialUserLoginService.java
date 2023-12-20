package plus.plusassignment.api.user.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.SocialUserInfo;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.domain.user.service.SocialUserService;
import plus.plusassignment.global.exception.user.EmailAlreadyExistException;
import plus.plusassignment.global.jwt.JwtManager;
import plus.plusassignment.global.jwt.TokenLoginDTO;

@Service
@RequiredArgsConstructor
public class ApiSocialUserLoginService {

    private final SocialUserService socialUserService;

    private final JwtManager jwtManager;

    @Transactional
    public TokenLoginDTO socialLogin(String accessToken, String socialType) {

        SocialUserInfo socialUserInfo = getSocialUserInfo(accessToken, socialType);

        Optional<SocialUser> optionalSocialUser = socialUserService.findByEmail(
                socialUserInfo.email());

        if (optionalSocialUser.isPresent()) {
            String username = optionalSocialUser.get().getUsername();
            return jwtManager.createAccessAndRefreshToken(username);
        }

        SocialUser socialUser = socialUserService.registerSocialUser(
                socialUserInfo.toEntity());

        return jwtManager.createAccessAndRefreshToken(socialUser.getUsername());
    }

    private SocialUserInfo getSocialUserInfo(String accessToken, String socialType) {

        OauthLoginService oauthLoginService =
                OauthLoginServiceFactory.getOauthLoginService(socialType);

        return oauthLoginService.getUserInfo(accessToken);
    }

    public void validateDuplicateEmail(String email) {
        socialUserService.findByEmail(email)
                .ifPresent(user -> {throw new EmailAlreadyExistException();});
    }

}
