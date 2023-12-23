package plus.plusassignment.api.post.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import plus.plusassignment.domain.post.entity.Post;

public class PostSaveDTO {

    public record Request(@Length(min = 1, max = 500) String title,
                          @Length(min = 1, max = 5000)String content) {

        public Post toEntity(String userId) {
            return Post.builder()
                    .title(title)
                    .content(content)
                    .userId(userId)
                    .deleted(false)
                    .build();
        }
    }

    @Builder
    public record Response(
            Long id,
            String title,
            String content,
            String poster,
            LocalDateTime createdTime
    ) {

        public static Response of(Post post, String username) {
            return Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .poster(username)
                    .createdTime(post.getCreatedTime())
                    .build();
        }

    }


}
