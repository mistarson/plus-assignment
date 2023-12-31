package plus.plusassignment.api.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.post.dto.PostGetDTO;
import plus.plusassignment.api.post.dto.PostModifyDTO;
import plus.plusassignment.api.post.dto.PostSaveDTO;
import plus.plusassignment.api.post.service.ApiPostService;
import plus.plusassignment.global.argumentresolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class ApiPostController {

    private final ApiPostService apiPostService;

    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostSaveDTO.Request requestDTO,
            @LoginUserId String userId) {

        PostSaveDTO.Response response = apiPostService.savePost(requestDTO, userId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {

        PostGetDTO postGetDTO = apiPostService.getPost(postId);

        return ResponseEntity.ok(postGetDTO);
    }

    @GetMapping
    public ResponseEntity<?> getPosts(Pageable pageable) {

        Page<PostGetDTO> postGetDTOS = apiPostService.getPosts(pageable);

        return ResponseEntity.ok(postGetDTOS);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> modifyPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostModifyDTO.Request requestDTO,
            @LoginUserId String userId) {

        PostModifyDTO.Response responseDTO = apiPostService.modifyPost(postId, requestDTO, userId);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @PathVariable Long postId,
            @LoginUserId String userId) {

        apiPostService.deletePost(postId, userId);

        return ResponseEntity.ok("삭제 성공");
    }
}
