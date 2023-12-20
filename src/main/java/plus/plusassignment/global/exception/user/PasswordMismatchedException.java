package plus.plusassignment.global.exception.user;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class PasswordMismatchedException extends BusinessException {

    public PasswordMismatchedException() {
        super(ErrorCode.MISMATCHED_PASSWORD_EXCEPTION);
    }
}
