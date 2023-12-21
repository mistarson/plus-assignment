package plus.plusassignment.api.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import plus.plusassignment.api.user.dto.SocialUserInfo;
import plus.plusassignment.domain.user.constant.SocialType;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.domain.user.service.SocialUserService;
import plus.plusassignment.global.jwt.JwtManager;

@ExtendWith(MockitoExtension.class)
class ApiSocialUserLoginServiceTest {

    @InjectMocks
    ApiSocialUserLoginService apiSocialUserLoginService;

    @Mock
    SocialUserService socialUserService;

    @Mock
    JwtManager jwtManager;

    @Mock
    OauthLoginService oauthLoginService;

    static MockedStatic<OauthLoginServiceFactory> oauthLoginServiceFactory;

    static SocialUser socialUser;

    static SocialUserInfo socialUserInfo;

    @BeforeAll
    static void initUser() {

        socialUser = SocialUser.builder()
                .id("social_1")
                .email("yeowuli2@naver.com")
                .username("손창현")
                .socialType(SocialType.KAKAO)
                .build();

        socialUserInfo =
                new SocialUserInfo("손창현", "yeowuli2@naver.com", SocialType.KAKAO);

        oauthLoginServiceFactory = mockStatic(OauthLoginServiceFactory.class);
    }

    @Nested
    @DisplayName("요청한 이메일을 가진 회원이 있다면 true, 아니라면 false를 반환한다.")
    class ExistsUserByEmail {

        String email = socialUser.getEmail();

        @Test
        @DisplayName("요청한 이메일을 가진 회원이 있다면, true를 반환한다.")
        void findByUserByEmailReturnTrue() {
            //given
            given(socialUserService.findByEmail(email)).willReturn(
                    Optional.of(socialUser));

            // when
            boolean existsed = apiSocialUserLoginService.existsUserByEmail(email);

            // then
            Assertions.assertTrue(existsed);
        }

        @Test
        @DisplayName("요청한 이메일을 가진 회원이 없다면, false를 반환한다")
        void findByUserByEmailReturnFalse(){
            //given
            given(socialUserService.findByEmail(email)).willReturn(
                    Optional.empty());

            // when
            boolean existsed = apiSocialUserLoginService.existsUserByEmail(email);

            // then
            Assertions.assertFalse(existsed);
        }
    }

    @Nested
    @DisplayName("회원가입 테스트")
    class SignUpSocialUser {

        String accessToken = "accessToken";
        String socialType = SocialType.KAKAO.toString();

        @Test
        @DisplayName("요청한 회원정보가 DB에 없다면 회원가입을 진행시킨다.")
        void signupSocialUser(){
            //given
            given(OauthLoginServiceFactory.getOauthLoginService(socialType)).willReturn(
                    oauthLoginService);
            given(oauthLoginService.getUserInfo(accessToken)).willReturn(socialUserInfo);
            given(socialUserService.findByEmail(socialUserInfo.email())).willReturn(
                    Optional.empty());

            // when
            apiSocialUserLoginService.socialLogin(accessToken, socialType);

            // then
            verify(socialUserService).registerSocialUser(any());
        }
    }

}