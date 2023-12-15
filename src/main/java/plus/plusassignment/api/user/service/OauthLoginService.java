package plus.plusassignment.api.user.service;

import plus.plusassignment.api.user.dto.SocialUserInfo;

public interface OauthLoginService {

    SocialUserInfo getUserInfo(String accessToken);

}
