package plus.plusassignment.api.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.post.dto.PostSaveDTO;
import plus.plusassignment.api.post.dto.PostSaveDTO.Request;
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
}
