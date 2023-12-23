package plus.plusassignment.api.comment.dto;

import lombok.Builder;
import plus.plusassignment.domain.comment.entity.Comment;

public class CommentSaveDTO {

    public record Request(String content) {}

    @Builder
    public record Response(
            Long commentId,
            String content,
            Long postId,
            String userId){

        public static Response of(Comment comment, Long postId, String userId) {
            return Response.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .postId(postId)
                    .userId(userId)
                    .build();
        }

    }

}
