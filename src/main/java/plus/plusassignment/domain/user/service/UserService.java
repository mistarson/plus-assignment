package plus.plusassignment.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.entity.NormalUser;
import plus.plusassignment.domain.user.entity.SocialUser;
import plus.plusassignment.global.utils.UserUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NormalUserService normalUserService;

    private final SocialUserService socialUserService;

    public String getUsernameFromUserId(String userId) {

        if (UserUtils.isSocialUser(userId)) {
            SocialUser socialUser = socialUserService.findById(userId);
            return socialUser.getUsername();
        }

        NormalUser normalUser = normalUserService.findById(userId);
        return normalUser.getUsername();
    }
}
