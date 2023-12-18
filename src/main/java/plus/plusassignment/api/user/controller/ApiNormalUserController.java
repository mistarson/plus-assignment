package plus.plusassignment.api.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.user.dto.NormalUserLoginDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO;
import plus.plusassignment.api.user.dto.NormalUserRegisterDTO.Response;
import plus.plusassignment.api.user.service.ApiNormalUserService;
import plus.plusassignment.global.jwt.TokenLoginDTO;

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

    @PostMapping("/login")
    public ResponseEntity<?> loginNormalUser(
            @Valid @RequestBody NormalUserLoginDTO normalUserLoginDTO) {

        TokenLoginDTO jwtLoginResponseDTO = apiNormalUserService.loginNormalUser(
                normalUserLoginDTO);

        return ResponseEntity.ok(jwtLoginResponseDTO);
    }

    @GetMapping("/duplication-username")
    public ResponseEntity<?> validateDuplicateUsername(@RequestParam String username) {

        apiNormalUserService.validateDuplicateUsername(username);

        return ResponseEntity.ok("사용 가능");
    }

    @GetMapping("/duplication-email")
    public ResponseEntity<?> validateDuplicateEmail(@RequestParam String email) {

        apiNormalUserService.validateDuplicateEmail(email);

        return ResponseEntity.ok("사용 가능");
    }

}
