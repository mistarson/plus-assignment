package plus.plusassignment.domain.post.service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.post.dao.PostWithUser;
import plus.plusassignment.domain.post.entity.Post;
import plus.plusassignment.domain.post.repository.PostRepository;
import plus.plusassignment.global.exception.post.PostNotFoundException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public Post findByIdNotDeleted(Long postId) {
        return postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(PostNotFoundException::new);
    }

    public Page<PostWithUser> getPostAllWithUser(Pageable pageable) {
        return postRepository.findAllPostsWithUser(pageable);
    }

    public void modifyPost(Post post, Post modifyingPost) {
        post.modify(modifyingPost);
    }

    public void deletePost(Post post) {
        post.deletePost();
    }

    @Transactional
    public void delete90DaysOldData(LocalDateTime diffTime) {

        List<Post> oldPosts = postRepository.findAllByCreatedTimeLessThanEqual(diffTime);

        oldPosts.forEach(Post::deletePost);
    }
}
