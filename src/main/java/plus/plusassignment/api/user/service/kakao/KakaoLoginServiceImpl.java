package plus.plusassignment.api.user.service.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.api.user.dto.SocialUserInfo;
import plus.plusassignment.api.user.service.OauthLoginService;

@Service
@RequiredArgsConstructor
public class KakaoLoginServiceImpl implements OauthLoginService {

    private final KakaoFeignClient kakaoFeignClient;

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public SocialUserInfo getUserInfo(String accessToken) {
        KakaoUserInfo kakaoUserInfo = kakaoFeignClient.getKakaoUserInfo(CONTENT_TYPE, accessToken);

        return kakaoUserInfo.toSocialUserInfo();
    }
}
