package plus.plusassignment.global.utils;

import plus.plusassignment.domain.user.constant.UserConstant;

public class UserUtils {

    public static boolean isSocialUser(String userId) {
        return userId.startsWith(UserConstant.SOCIAL_USER_ID_PREFIX);
    }

}
