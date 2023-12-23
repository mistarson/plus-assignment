package plus.plusassignment.domain.comment.dao;

import java.time.LocalDateTime;

public record CommentWithPostAndUser(
        Long commentId,
        String commentContent,
        LocalDateTime commentCreatedTime,
        Long postId,
        String postTitle,
        String postContent,
        LocalDateTime postCreatedTime,
        String username,
        String email
) {}
