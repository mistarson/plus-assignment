package plus.plusassignment.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.post.dao.PostWithUserDAO;
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

    public Post findById(Long postId) {
        return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    }

    public Page<PostWithUserDAO> getPostAllWithUserDAO(Pageable pageable) {
        return postRepository.findAllPostsWithUser(pageable);
    }

    public void modifyPost(Post post, Post modifyingPost) {
        post.modify(modifyingPost);
    }
}
