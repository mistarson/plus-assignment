package plus.plusassignment.api.comment.service;


import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.comment.dto.CommentGetDTO;
import plus.plusassignment.api.comment.dto.CommentSaveDTO;
import plus.plusassignment.domain.comment.dao.CommentWithPostAndUser;
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
        String username = userService.getUsernameFromUserId(userId);

        Comment savedComment = commentService.saveComment(
                Comment.createComment(requestDTO.content(), post, userId));

        return CommentSaveDTO.Response.of(savedComment, postId, username);
    }

    public CommentGetDTO getComment(Long commentId, String userId) {

        CommentWithPostAndUser commentByIdWithPostAndUser =
                commentService.findCommentByIdWithPostAndUser(commentId);

        return CommentGetDTO.from(commentByIdWithPostAndUser);
    }

    public Page<CommentGetDTO> getComments(Long postId, Pageable pageable) {

        Page<CommentWithPostAndUser> commentWithPostAndUsers =
                commentService.findAllByIdWithPostAndUser(postId, pageable);

        List<CommentWithPostAndUser> commentWithPostAndUsersContent =
                commentWithPostAndUsers.getContent();
        List<CommentGetDTO> commentGetDTOContent = commentWithPostAndUsersContent.stream()
                .map(CommentGetDTO::from)
                .collect(Collectors.toList());

        return new PageImpl<>(commentGetDTOContent, commentWithPostAndUsers.getPageable(),
                commentWithPostAndUsers.getTotalElements());
    }
}
