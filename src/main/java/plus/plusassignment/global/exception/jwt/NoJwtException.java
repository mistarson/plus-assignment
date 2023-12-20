package plus.plusassignment.global.exception.jwt;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class NoJwtException extends BusinessException {

    public NoJwtException() {
        super(ErrorCode.NO_JWT_EXCEPTION);
    }
}
