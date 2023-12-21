package plus.plusassignment.api.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.post.dto.PostSaveDTO;
import plus.plusassignment.api.post.service.ApiPostService;
import plus.plusassignment.global.argumentresolver.LoginUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class ApiPostController {

    private final ApiPostService apiPostService;

    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostSaveDTO.Request requestDTO,
            @LoginUserId String userId) {

        PostSaveDTO.Response response = apiPostService.savePost(requestDTO, userId);

        return ResponseEntity.ok(response);
    }

}
