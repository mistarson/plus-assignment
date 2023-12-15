package plus.plusassignment.domain.user.service;

import static plus.plusassignment.domain.user.constant.UserConstant.SOCIAL_USER_ID_PREFIX;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.domain.user.repository.SocialUserRepository;

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

}
