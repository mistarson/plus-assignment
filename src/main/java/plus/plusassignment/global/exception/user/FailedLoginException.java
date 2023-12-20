package plus.plusassignment.global.exception.user;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class FailedLoginException extends BusinessException {

    public FailedLoginException(Throwable cause) {
        super(ErrorCode.FAILED_LOGIN_EXCEPTION, cause);
    }
}
