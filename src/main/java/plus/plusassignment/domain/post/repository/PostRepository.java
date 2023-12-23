package plus.plusassignment.domain.post.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import plus.plusassignment.domain.post.entity.Post;
import plus.plusassignment.domain.post.repository.custom.CustomPostRepository;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

    Optional<Post> findByIdAndDeletedFalse(Long postId);

}
