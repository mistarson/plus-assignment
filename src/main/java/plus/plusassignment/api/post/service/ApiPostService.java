package plus.plusassignment.api.post.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.post.dto.PostGetDTO;
import plus.plusassignment.api.post.dto.PostModifyDTO;
import plus.plusassignment.api.post.dto.PostModifyDTO.Response;
import plus.plusassignment.api.post.dto.PostSaveDTO;
import plus.plusassignment.api.post.dto.PostSaveDTO.Request;
import plus.plusassignment.domain.post.dao.PostWithUser;
import plus.plusassignment.domain.post.entity.Post;
import plus.plusassignment.domain.post.service.PostService;
import plus.plusassignment.domain.user.service.UserService;
import plus.plusassignment.global.exception.common.AuthorizationException;
import plus.plusassignment.global.exception.common.ErrorCode;

@Service
@RequiredArgsConstructor
public class ApiPostService {

    private final PostService postService;

    private final UserService userService;

    @Transactional
    public PostSaveDTO.Response savePost(Request requestDTO, String userId) {

        Post savedPost = postService.savePost(requestDTO.toEntity(userId));
        String username = userService.getUsernameFromUserId(userId);

        return PostSaveDTO.Response.of(savedPost, username);
    }

    public PostGetDTO getPost(Long postId) {

        Post post = postService.findByIdNotDeleted(postId);
        String username = userService.getUsernameFromUserId(post.getUserId());

        return PostGetDTO.of(post, username);
    }

    public Page<PostGetDTO> getPosts(Pageable pageable) {

        Page<PostWithUser> postWithUser = postService.getPostAllWithUser(pageable);

        List<PostWithUser> postWithUserContent = postWithUser.getContent();
        List<PostGetDTO> postGetDTOContent = postWithUserContent.stream().map(PostGetDTO::from)
                .collect(Collectors.toList());

        return new PageImpl<>(postGetDTOContent, postWithUser.getPageable(), postWithUser.getTotalElements());
    }

    @Transactional
    public Response modifyPost(Long postId, PostModifyDTO.Request requestDTO, String userId) {

        Post post = postService.findByIdNotDeleted(postId);

        validateModifyingAuthority(post.getUserId(), userId);

        postService.modifyPost(post, requestDTO.toEntity());
        String username = userService.getUsernameFromUserId(post.getUserId());

        return PostModifyDTO.Response.of(post, username);
    }

    @Transactional
    public void deletePost(Long postId, String userId) {

        Post post = postService.findByIdNotDeleted(postId);

        validateModifyingAuthority(userId, post.getUserId());

        postService.deletePost(post);
    }

    public void validateModifyingAuthority(String userIdFromDB, String userIdFromRequest) {

        if (!userIdFromDB.equals(userIdFromRequest)) {
            throw new AuthorizationException(ErrorCode.REJECT_MODIFIYING_POST_EXCEPTION);
        }
    }
}
