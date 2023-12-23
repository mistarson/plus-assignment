package plus.plusassignment.domain.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import plus.plusassignment.domain.common.BaseTime;
import plus.plusassignment.domain.post.entity.Post;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5000)
    private String content;

    @JoinColumn
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private boolean deleted;

    public static Comment createComment(String content, Post post, String userId) {
        return Comment.builder()
                .content(content)
                .post(post)
                .userId(userId)
                .deleted(false)
                .build();
    }

}
