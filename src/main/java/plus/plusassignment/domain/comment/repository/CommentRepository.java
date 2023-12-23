package plus.plusassignment.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plus.plusassignment.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
