package plus.plusassignment.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.NormalUserLoginDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.service.NormalUserService;
import plus.plusassignment.global.jwt.TokenLoginDTO;
import plus.plusassignment.global.jwt.JwtManager;

@Service
@RequiredArgsConstructor
public class ApiNormalUserService {

    private final NormalUserService normalUserService;

    private final PasswordEncoder passwordEncoder;

    private final JwtManager jwtManager;

    public NormalUserRegisterDTO.Response registerNormalUser(
            NormalUserRegisterDTO.Request requestDTO) {

        validateDuplicateUser(requestDTO);

        NormalUser savedNormalUser = normalUserService.registerNormalUser(
                requestDTO.toEntity(passwordEncoder));

        return NormalUserRegisterDTO.Response.from(savedNormalUser);
    }

    private void validateDuplicateUser(NormalUserRegisterDTO.Request requestDTO) {

        normalUserService.findByUsernameIfPresentThrowException(requestDTO.username());
        normalUserService.findByEmailIfPresentThrowException(requestDTO.email());
    }

    public TokenLoginDTO loginNormalUser(NormalUserLoginDTO normalUserLoginDTO) {

        NormalUser normalUser = normalUserService.findByUsername(normalUserLoginDTO.username());

        validatePassword(normalUserLoginDTO.password(), normalUser.getPassword());

        return jwtManager.createAccessAndRefreshToken(normalUserLoginDTO.username());
    }

    private void validatePassword(String password, String encodedPassword) {

        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void validateDuplicateUsername(String username) {
        normalUserService.findByUsernameIfPresentThrowException(username);
    }

    public void validateDuplicateEmail(String email) {
        normalUserService.findByEmailIfPresentThrowException(email);
    }
}
