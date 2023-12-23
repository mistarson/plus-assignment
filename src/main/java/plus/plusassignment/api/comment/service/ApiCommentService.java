package plus.plusassignment.api.comment.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.comment.dto.CommentSaveDTO;
import plus.plusassignment.domain.comment.entity.Comment;
import plus.plusassignment.domain.comment.service.CommentService;
import plus.plusassignment.domain.post.entity.Post;
import plus.plusassignment.domain.post.service.PostService;
import plus.plusassignment.domain.user.service.UserService;

@Service
@RequiredArgsConstructor
public class ApiCommentService {

    private final CommentService commentService;

    private final PostService postService;

    private final UserService userService;

    @Transactional
    public CommentSaveDTO.Response saveComment(CommentSaveDTO.Request requestDTO, Long postId,
            String userId) {

        Post post = postService.findByIdNotDeleted(postId);
        userService.validateExistsUserById(userId);

        Comment savedComment = commentService.saveComment(
                Comment.createComment(requestDTO.content(), post, userId));

        return CommentSaveDTO.Response.of(savedComment, postId, userId);
    }

}
