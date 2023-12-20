package plus.plusassignment.global.exception.user;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.NOT_FOUND_USER_EXCEPTION);
    }
}
