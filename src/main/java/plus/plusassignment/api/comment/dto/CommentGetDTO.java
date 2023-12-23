package plus.plusassignment.api.comment.dto;

import lombok.Builder;
import plus.plusassignment.domain.comment.dao.CommentWithPostAndUser;

@Builder
public record CommentGetDTO(
        Long commentId,
        String content,
        Long postId,
        String username
) {

    public static CommentGetDTO from(CommentWithPostAndUser commentByIdWithPostAndUser) {
        return CommentGetDTO.builder()
                .commentId(commentByIdWithPostAndUser.commentId())
                .content(commentByIdWithPostAndUser.commentContent())
                .postId(commentByIdWithPostAndUser.postId())
                .username(commentByIdWithPostAndUser.username())
                .build();
    }
}
