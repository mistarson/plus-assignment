package plus.plusassignment.global.exception.mailauth;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class AuthCodeNotFoundException extends BusinessException {

    public AuthCodeNotFoundException() {
        super(ErrorCode.NOT_FOUND_AUTH_CODE_EXCEPTION);
    }
}
