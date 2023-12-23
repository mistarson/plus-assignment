package plus.plusassignment.domain.user.service;

import static plus.plusassignment.domain.user.constant.UserConstant.SOCIAL_USER_ID_PREFIX;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.domain.user.repository.SocialUserRepository;
import plus.plusassignment.global.exception.user.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class SocialUserService {

    private final SocialUserRepository socialUserRepository;

    public Optional<SocialUser> findByEmail(String email) {

        return socialUserRepository.findByEmail(email);
    }

    @Transactional
    public SocialUser registerSocialUser(SocialUser socialUser) {

        Long id = socialUserRepository.getIdFromSeqUser();
        socialUser.setIdForRegister(SOCIAL_USER_ID_PREFIX + id);

        return socialUserRepository.save(socialUser);
    }

    public SocialUser findById(String userId) {
        return socialUserRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    public void validateExistsUserById(String userId) {

        boolean isExistsUser = socialUserRepository.existsById(userId);

        if (isExistsUser) {
            throw new UserNotFoundException();
        }
    }
}
