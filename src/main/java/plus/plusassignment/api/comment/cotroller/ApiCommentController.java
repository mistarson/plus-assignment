package plus.plusassignment.api.comment.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.comment.dto.CommentSaveDTO;
import plus.plusassignment.api.comment.service.ApiCommentService;
import plus.plusassignment.global.argumentresolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class ApiCommentController {

    private final ApiCommentService apiCommentService;

    @PostMapping("/posts/{postId}")
    public ResponseEntity<?> saveComment(
            @Valid @RequestBody CommentSaveDTO.Request requestDTO,
            @PathVariable Long postId, @LoginUserId String userId) {

        CommentSaveDTO.Response responseDTO = apiCommentService.saveComment(requestDTO, postId,
                userId);

        return ResponseEntity.ok(responseDTO);
    }

}
