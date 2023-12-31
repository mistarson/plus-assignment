package plus.plusassignment.api.user.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import plus.plusassignment.api.user.dto.NormalUserLoginDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO.Request;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO.Response;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.redis.MailAuthCode;
import plus.plusassignment.domain.user.redis.MailAuthCodeService;
import plus.plusassignment.domain.user.service.NormalUserService;
import plus.plusassignment.global.exception.mailauth.AuthCodeMismatchedException;
import plus.plusassignment.global.exception.user.EmailAlreadyExistException;
import plus.plusassignment.global.exception.user.FailedLoginException;
import plus.plusassignment.global.jwt.JwtManager;

@ExtendWith(MockitoExtension.class)
class ApiNormalUserServiceTest {

    @InjectMocks
    ApiNormalUserService apiNormalUserService;

    @Mock
    NormalUserService normalUserService;

    @Mock
    MailAuthCodeService mailAuthCodeService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtManager jwtManager;

    static NormalUser normalUser;

    @BeforeAll
    static void initUser() {
        normalUser = NormalUser.builder()
                .id("normal_1")
                .email("yeowuli2@naver.com")
                .username("손창현")
                .password("123456")
                .build();
    }

    @Nested
    @DisplayName("이메일 중복 검증 테스트")
    class ValidateDuplicateEmail {

        String email = "yeowuli2@naver.com";

        @Test
        @DisplayName("요청한 이메일을 가진 회원이 있다면 예외가 발생한다.")
        void validateDuplicateEmailNotThrowException(){
            //given
            given(normalUserService.findByEmail(email)).willReturn(Optional.of(normalUser));

            // when - then
            assertThatThrownBy(
                    () -> apiNormalUserService.validateDuplicateEmail(email)).isInstanceOf(
                    EmailAlreadyExistException.class);
        }
    }

    @Nested
    @DisplayName("이메일 인증 코드 검증 테스트")
    class ValidateEmailAuthCode {

        String email = "yeowuli2@naver.com";
        String authCode = "654321";

        MailAuthCode otherMailAuthCode = MailAuthCode.builder()
                .authId("yeowuli2@naver.com")
                .authCode("123456")
                .build();

        @Test
        @DisplayName("사용자가 입력한 이메일 인증코드와 실제 발급했던 인증코드가 일치하지 않다면 예외가 발생한다.")
        void validateEmailAuthCode(){
            //given
            given(mailAuthCodeService.findByAuthId(email)).willReturn(otherMailAuthCode);

            // when - then
            assertThatThrownBy(
                    () -> apiNormalUserService.validateEmailAuthCode(email, authCode)).isInstanceOf(
                    AuthCodeMismatchedException.class);
        }

    }

    @Nested
    @DisplayName("일반 회원 회원가입 테스트")
    class NormalUserSignup {

        String email = "yeowuli2@naver.com";
        String username = "손창현";
        String password = "123456";
        String confirmPassword = "123456";
        String authCode = "123456";

        NormalUserRegisterDTO.Request requestDTO = new Request(email, username, password,
                confirmPassword, authCode);

        MailAuthCode mailAuthCode = MailAuthCode.builder()
                .authId("yeowuli2@naver.com")
                .authCode("123456")
                .build();

        @Test
        @DisplayName("정상 회원가입 테스트")
        void signup() {
            //given
            given(normalUserService.findByEmail(requestDTO.email())).willReturn(Optional.empty());
            given(mailAuthCodeService.findByAuthId(requestDTO.email())).willReturn(mailAuthCode);
            given(normalUserService.registerNormalUser(any())).willReturn(normalUser);

            // when
            Response response = apiNormalUserService.registerNormalUser(requestDTO);

            // then
            assertEquals(requestDTO.email(), response.email());
            assertEquals(requestDTO.username(), response.username());
        }

        @Test
        @DisplayName("해당 이메일로 가입된 회원이 있다면 예외가 발생한다.")
        void signupNormalUserByDuplicateEmail() {
            //given
            given(normalUserService.findByEmail(requestDTO.email())).willReturn(
                    Optional.of(normalUser));

            // when - then
            assertThatThrownBy(
                    () -> apiNormalUserService.registerNormalUser(requestDTO)).isInstanceOf(
                    EmailAlreadyExistException.class);
        }

        @Test
        @DisplayName("이메일 인증 번호가 일치하지 않다면 예외가 발생한다.")
        void signupNormalUserByMismatchedAuthCode() {
            //given
            MailAuthCode otherMailAuthCode = MailAuthCode.builder()
                    .authId("yeowuli2@naver.com")
                    .authCode("654321")
                    .build();

            given(normalUserService.findByEmail(requestDTO.email())).willReturn(Optional.empty());
            given(mailAuthCodeService.findByAuthId(requestDTO.email())).willReturn(
                    otherMailAuthCode);

            // when - then
            assertThatThrownBy(
                    () -> apiNormalUserService.registerNormalUser(requestDTO)).isInstanceOf(
                    AuthCodeMismatchedException.class);
        }
    }

    @Nested
    @DisplayName("일반 회원 로그인 테스트")
    class NormalUserLogin {

        String email = "yeowuli2@naver.com";
        String password = "123456";

        NormalUserLoginDTO requestDTO = new NormalUserLoginDTO(email, password);

        @Test
        @DisplayName("정상 로그인 테스트")
        void loginNormalUser() {
            //given
            given(normalUserService.findByEmail(requestDTO.email())).willReturn(
                    Optional.of(normalUser));
            given(passwordEncoder.matches(requestDTO.password(),
                    normalUser.getPassword())).willReturn(true);

            // when - then
            apiNormalUserService.loginNormalUser(requestDTO);
        }

        @Test
        @DisplayName("요청한 이메일을 가진 회원이 없다면 예외가 발생한다.")
        void loginNormalUserByNotExistUser() {
            //given
            given(normalUserService.findByEmail(requestDTO.email()))
                    .willReturn(Optional.empty());

            // when - then
            assertThatThrownBy(() -> apiNormalUserService.loginNormalUser(requestDTO)).isInstanceOf(
                    FailedLoginException.class);
        }

        @Test
        @DisplayName("요청한 비밀번호가 일치하지 않는다면 예외가 발생한다.")
        void loginNormalUserByMismatchedPassword(){
            //given
            given(normalUserService.findByEmail(requestDTO.email())).willReturn(
                    Optional.of(normalUser));
            given(passwordEncoder.matches(requestDTO.password(),
                    normalUser.getPassword())).willReturn(false);

            // when - then
            assertThatThrownBy(() -> apiNormalUserService.loginNormalUser(requestDTO)).isInstanceOf(
                    FailedLoginException.class);
        }
    }
}