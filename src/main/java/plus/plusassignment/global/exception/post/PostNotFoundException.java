package plus.plusassignment.global.exception.post;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class PostNotFoundException extends BusinessException {

    public PostNotFoundException() {
        super(ErrorCode.NOT_FOUND_POST_EXCEPTION);
    }
}
