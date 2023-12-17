package plus.plusassignment.web.kakaotoken;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenClient kakaoTokenClient;

    private final String CLIENT_ID = "22846f7f8f89ab9ddf7a13ea8c57f017";
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String GRANT_TYPE = "authorization_code";
    private final String REDIRECT_URI = "http://localhost:8080/auth/kakao/callback";

    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {

        KakaoTokenResponse kakaoTokenResponse = kakaoTokenClient.getKakaoToken(CONTENT_TYPE,
                GRANT_TYPE, CLIENT_ID, REDIRECT_URI, code);

        return ResponseEntity.ok(kakaoTokenResponse);
    }

}
