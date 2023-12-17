package plus.plusassignment.domain.user.constant;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum SocialType {

    KAKAO("KAKAO", "kakaoLoginServiceImpl"),
    NAVER("NAVER", "naverLoginServiceImpl");

    private static final Map<String, String> SOCIAL_TYPE_MAP = Collections.unmodifiableMap(
            Stream.of(values()).collect(
                    Collectors.toMap(SocialType::getSocialType,SocialType::getServiceBeanName))
    );

    private final String socialType;
    private final String serviceBeanName;

    SocialType(String socialType, String serviceBeanName) {
        this.socialType = socialType;
        this.serviceBeanName = serviceBeanName;
    }

    public static String getOauthServiceBeanName(String socialType) {
        return SOCIAL_TYPE_MAP.get(socialType);
    }

//    @JsonValue
//    public String getSocialType() {
//        return socialType;
//    }
//
//    public String getServiceBeanName() {
//        return serviceBeanName;
//    }
}
