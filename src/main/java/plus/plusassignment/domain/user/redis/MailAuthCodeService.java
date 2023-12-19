package plus.plusassignment.domain.user.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailAuthCodeService {

    private final MailAuthCodeRepository mailAuthCodeRepository;

    public MailAuthCode findByAuthId(String authId) {
        return mailAuthCodeRepository.findById(authId)
                .orElseThrow(() -> new IllegalArgumentException("없는 인증코드입니다."));
    }

    public void save(MailAuthCode mailAuthCode) {
        mailAuthCodeRepository.save(mailAuthCode);
    }

}
