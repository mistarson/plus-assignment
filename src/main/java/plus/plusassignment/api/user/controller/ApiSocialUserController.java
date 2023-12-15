package plus.plusassignment.api.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.user.dto.SocialLoginDTO;
import plus.plusassignment.api.user.dto.SocialLoginDTO.Response;
import plus.plusassignment.api.user.service.ApiSocialUserLoginService;
import plus.plusassignment.global.jwt.TokenUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/social-users")
public class ApiSocialUserController {

    private final ApiSocialUserLoginService apiSocialUserLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> socialLogin(@Valid @RequestBody SocialLoginDTO.Request requestDTO
            , HttpServletRequest httpServletRequest) {

        String accessToken = TokenUtils.getTokenFromRequest(httpServletRequest);

        Response responseDTO = apiSocialUserLoginService.socialLogin(
                accessToken, requestDTO.socialType());

        return ResponseEntity.ok(responseDTO);
    }
}
