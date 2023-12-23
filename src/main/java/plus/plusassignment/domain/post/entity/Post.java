package plus.plusassignment.domain.post.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import plus.plusassignment.domain.common.BaseTime;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private boolean deleted;

    public void modify(Post modifyingPost) {

        if (StringUtils.hasText(modifyingPost.title)) {
            this.title = modifyingPost.getTitle();
        }

        if (StringUtils.hasText(modifyingPost.content)) {
            this.content = modifyingPost.getContent();
        }
    }

    public void deletePost() {
        this.deleted = true;
    }
}
