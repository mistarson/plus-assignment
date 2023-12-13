package plus.plusassignment.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO.Response;
import plus.plusassignment.api.user.service.ApiNormalUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/normal-users")
public class ApiNormalUserController {

    private final ApiNormalUserService apiNormalUserService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerNormalUser(
            @Valid @RequestBody NormalUserRegisterDTO.Request requestDTO) {

        Response responseDTO = apiNormalUserService.registerNormalUser(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

}
