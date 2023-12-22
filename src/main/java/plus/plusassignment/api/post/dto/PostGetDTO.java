package plus.plusassignment.api.post.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import plus.plusassignment.domain.post.dao.PostWithUserDAO;
import plus.plusassignment.domain.post.entity.Post;

@Builder
public record PostGetDTO(
        Long id,
        String title,
        String content,
        String poster,
        LocalDateTime createdTime
) {
    public static PostGetDTO of(Post post, String username) {
        return PostGetDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .poster(username)
                .createdTime(post.getCreatedTime())
                .build();
    }

    public static PostGetDTO from(PostWithUserDAO postWithUserDAO) {
        return PostGetDTO.builder()
                .id(postWithUserDAO.postId())
                .title(postWithUserDAO.postTitle())
                .content(postWithUserDAO.postContent())
                .poster(postWithUserDAO.posterName())
                .createdTime(postWithUserDAO.postCreatedTime())
                .build();
    }

}
