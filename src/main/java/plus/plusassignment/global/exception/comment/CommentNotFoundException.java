package plus.plusassignment.global.exception.comment;

import plus.plusassignment.global.exception.common.BusinessException;
import plus.plusassignment.global.exception.common.ErrorCode;

public class CommentNotFoundException extends BusinessException {

    public CommentNotFoundException() {
        super(ErrorCode.NOT_FOUND_COMMENT_EXCEPTION);
    }
}
