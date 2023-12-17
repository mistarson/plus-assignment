package plus.plusassignment.api.user.service.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "https://kapi.kakao.com", name = "kakaoUserClient")
public interface KakaoFeignClient {

    @GetMapping(value = "/v2/user/me")
    KakaoUserInfo getKakaoUserInfo(
            @RequestHeader("Content-type") String contentType,
            @RequestHeader("Authorization") String accessToken
    );

}
