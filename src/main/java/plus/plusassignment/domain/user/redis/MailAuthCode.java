package plus.plusassignment.domain.user.redis;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@RedisHash(value = "mailAuthCode", timeToLive = 300L)
public record MailAuthCode(@Id String authId,
                           String authCode) {

    public static MailAuthCode createMailAuthCode(String id, String authCode) {
        return MailAuthCode.builder()
                .authId(id)
                .authCode(authCode)
                .build();
    }
}
