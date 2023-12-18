package plus.plusassignment.domain.user.service;

import static plus.plusassignment.domain.user.constant.UserConstant.NORMAL_USER_ID_PREFIX;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.repository.NormalUserRepository;

@Service
@RequiredArgsConstructor
public class NormalUserService {

    private final NormalUserRepository normalUserRepository;

    public void findByUsernameIfPresentThrowException(String username) {

        normalUserRepository.findByUsername(username).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        });
    }

    public void findByEmailIfPresentThrowException(String email) {

        normalUserRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        });
    }

    public NormalUser findByUsername(String username) {
        return normalUserRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 정보가 없습니다."));
    }

    @Transactional
    public NormalUser registerNormalUser(NormalUser normalUser) {

        Long id = normalUserRepository.getIdFromSeqUser();
        normalUser.setIdForRegister(NORMAL_USER_ID_PREFIX + id);

        return normalUserRepository.save(normalUser);
    }

}
