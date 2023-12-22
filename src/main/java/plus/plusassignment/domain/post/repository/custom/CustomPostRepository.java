package plus.plusassignment.domain.post.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import plus.plusassignment.domain.post.dao.PostWithUserDAO;

public interface CustomPostRepository {

    Page<PostWithUserDAO> findPostsWithUser(Pageable pageable);

}
