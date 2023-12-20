package plus.plusassignment.global.exception.user;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class EmailAlreadyExistException extends BusinessException {

    public EmailAlreadyExistException() {
        super(ErrorCode.ALREADY_EXIST_EMAIL_EXCEPTION);
    }
}
