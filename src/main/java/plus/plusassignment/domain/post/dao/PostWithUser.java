package plus.plusassignment.domain.post.dao;

import java.time.LocalDateTime;

public record PostWithUser(Long postId,
                           String postTitle,
                           String postContent,
                           LocalDateTime postCreatedTime,
                           String posterId,
                           String posterName,
                           String posterEmail
                              ) {
}
