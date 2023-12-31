package plus.plusassignment.web.kakaotoken;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://kauth.kakao.com", name = "sdw")
public interface KakaoTokenClient {

    @PostMapping("/oauth/token")
    KakaoTokenResponse getKakaoToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code);

}
