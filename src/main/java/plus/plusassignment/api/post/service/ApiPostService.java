package plus.plusassignment.api.post.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.post.dto.PostGetDTO;
import plus.plusassignment.api.post.dto.PostSaveDTO;
import plus.plusassignment.api.post.dto.PostSaveDTO.Request;
import plus.plusassignment.domain.post.dao.PostWithUserDAO;
import plus.plusassignment.domain.post.entity.Post;
import plus.plusassignment.domain.post.service.PostService;
import plus.plusassignment.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class ApiPostService {

    private final PostService postService;

    private final UserService userService;

    public PostSaveDTO.Response savePost(Request requestDTO, String userId) {

        Post savedPost = postService.savePost(requestDTO.toEntity(userId));
        String username = userService.getUsernameFromUserId(userId);

        return PostSaveDTO.Response.of(savedPost, username);
    }

    public PostGetDTO getPost(Long postId) {

        Post post = postService.findById(postId);
        String username = userService.getUsernameFromUserId(post.getUserId());

        return PostGetDTO.of(post, username);
    }

    public Page<PostGetDTO> getPosts(Pageable pageable) {

        Page<PostWithUserDAO> postWithUserDAO = postService.getPostWithUserDAO(pageable);

        List<PostWithUserDAO> postWithUserDAOContent = postWithUserDAO.getContent();
        List<PostGetDTO> postGetDTOContent = postWithUserDAOContent.stream().map(PostGetDTO::from)
                .collect(Collectors.toList());

        return new PageImpl<>(postGetDTOContent, postWithUserDAO.getPageable(), postWithUserDAO.getTotalElements());
    }
}
