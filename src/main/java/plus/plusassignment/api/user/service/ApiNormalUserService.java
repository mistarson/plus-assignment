package plus.plusassignment.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.NormalUserLoginDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.redis.MailAuthCode;
import plus.plusassignment.domain.user.redis.MailAuthCodeService;
import plus.plusassignment.domain.user.service.NormalUserService;
import plus.plusassignment.global.exception.mailauth.AuthCodeMismatchedException;
import plus.plusassignment.global.exception.user.EmailAlreadyExistException;
import plus.plusassignment.global.exception.user.FailedLoginException;
import plus.plusassignment.global.exception.user.PasswordMismatchedException;
import plus.plusassignment.global.exception.user.UserNotFoundException;
import plus.plusassignment.global.jwt.JwtManager;
import plus.plusassignment.global.jwt.TokenLoginDTO;

@Service
@RequiredArgsConstructor
public class ApiNormalUserService {

    private final NormalUserService normalUserService;

    private final MailAuthCodeService mailAuthCodeService;

    private final PasswordEncoder passwordEncoder;

    private final JwtManager jwtManager;

    public NormalUserRegisterDTO.Response registerNormalUser(
            NormalUserRegisterDTO.Request requestDTO) {

        validateDuplicateEmail(requestDTO.email());
        validateEmailAuthCode(requestDTO.email(), requestDTO.authCode());

        NormalUser savedNormalUser = normalUserService.registerNormalUser(
                requestDTO.toEntity(passwordEncoder));

        return NormalUserRegisterDTO.Response.from(savedNormalUser);
    }

    public void validateDuplicateEmail(String email) {

        normalUserService.findByEmail(email).ifPresent(normalUser -> {
            throw new EmailAlreadyExistException();
        });
    }

    public void validateEmailAuthCode(String email, String authCode) {

        MailAuthCode mailAuthCode = mailAuthCodeService.findByAuthId(email);

        if (!mailAuthCode.authCode().equals(authCode)) {
            throw new AuthCodeMismatchedException();
        }
    }

    public TokenLoginDTO loginNormalUser(NormalUserLoginDTO normalUserLoginDTO) {

        try {
            NormalUser normalUser = normalUserService.findByEmail(normalUserLoginDTO.email())
                    .orElseThrow(UserNotFoundException::new);

            validatePassword(normalUserLoginDTO.password(), normalUser.getPassword());

            return jwtManager.createAccessAndRefreshToken(normalUser.getId());
        } catch (UserNotFoundException | PasswordMismatchedException e) {
            throw new FailedLoginException(e);
        }
    }

    private void validatePassword(String password, String encodedPassword) {

        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new PasswordMismatchedException();
        }
    }
}
