package plus.plusassignment.global.exception.user;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class UserAlreadyExistException extends BusinessException {

    public UserAlreadyExistException() {
        super(ErrorCode.ALREADY_EXIST_USER_NAME_EXCEPTION);
    }
}
