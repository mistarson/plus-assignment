package plus.plusassignment.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.service.NormalUserService;

@Service
@RequiredArgsConstructor
public class ApiNormalUserService {

    private final NormalUserService normalUserService;

    public NormalUserRegisterDTO.Response registerNormalUser(
            NormalUserRegisterDTO.Request requestDTO) {

        validateDuplicateUser(requestDTO);

        NormalUser savedNormalUser = normalUserService.registerNormalUser(requestDTO.toEntity());

        return NormalUserRegisterDTO.Response.from(savedNormalUser);
    }

    private void validateDuplicateUser(NormalUserRegisterDTO.Request requestDTO) {

        normalUserService.findByUsernameIfPresentThrowException(requestDTO.username());
        normalUserService.findByEmailIfPresentThrowException(requestDTO.email());
    }

}
