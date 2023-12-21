package plus.plusassignment.domain.user.service;

import static plus.plusassignment.domain.user.constant.UserConstant.NORMAL_USER_ID_PREFIX;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.repository.NormalUserRepository;
import plus.plusassignment.global.exception.user.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class NormalUserService {

    private final NormalUserRepository normalUserRepository;

    public Optional<NormalUser> findByEmail(String email) {
        return normalUserRepository.findByEmail(email);
    }

    @Transactional
    public NormalUser registerNormalUser(NormalUser normalUser) {

        Long id = normalUserRepository.getIdFromSeqUser();
        normalUser.setIdForRegister(NORMAL_USER_ID_PREFIX + id);

        return normalUserRepository.save(normalUser);
    }

    public NormalUser findById(String userId) {
        return normalUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public boolean existById(String id) {
        return normalUserRepository.existsById(id);
    }
}
