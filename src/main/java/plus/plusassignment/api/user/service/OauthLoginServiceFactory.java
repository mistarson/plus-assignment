package plus.plusassignment.api.user.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.constant.SocialType;

@Service
public class OauthLoginServiceFactory {

    private static Map<String, OauthLoginService> oauthLoginServices;

    public OauthLoginServiceFactory(Map<String, OauthLoginService> oauthLoginServices) {
        OauthLoginServiceFactory.oauthLoginServices = oauthLoginServices;
    }

    public static OauthLoginService getOauthLoginService(String socialType) {

        String oauthServiceBeanName = SocialType.getOauthServiceBeanName(socialType);

        return oauthLoginServices.get(oauthServiceBeanName);
    }

}
