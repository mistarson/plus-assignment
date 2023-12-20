package plus.plusassignment.global.exception.jwt;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class JwtInvalidException extends BusinessException {

    public JwtInvalidException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
