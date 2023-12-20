package plus.plusassignment.domain.user.service;

import static plus.plusassignment.domain.user.constant.UserConstant.NORMAL_USER_ID_PREFIX;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.repository.NormalUserRepository;
import plus.plusassignment.global.exception.user.EmailAlreadyExistException;
import plus.plusassignment.global.exception.user.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class NormalUserService {

    private final NormalUserRepository normalUserRepository;

    public void findByEmailIfPresentThrowException(String email) {

        normalUserRepository.findByEmail(email).ifPresent(user -> {
            throw new EmailAlreadyExistException();
        });
    }

    public NormalUser findByUsername(String username) {
        return normalUserRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public NormalUser registerNormalUser(NormalUser normalUser) {

        Long id = normalUserRepository.getIdFromSeqUser();
        normalUser.setIdForRegister(NORMAL_USER_ID_PREFIX + id);

        return normalUserRepository.save(normalUser);
    }

}
