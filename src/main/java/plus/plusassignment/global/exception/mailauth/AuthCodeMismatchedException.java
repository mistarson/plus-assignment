package plus.plusassignment.global.exception.mailauth;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class AuthCodeMismatchedException extends BusinessException {

    public AuthCodeMismatchedException() {
        super(ErrorCode.MISMATCHED_AUTH_CODE_EXCEPTION);
    }
}
