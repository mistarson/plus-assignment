package plus.plusassignment.domain.user.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.plusassignment.global.exception.mailauth.AuthCodeNotFoundException;

@Service
@RequiredArgsConstructor
public class MailAuthCodeService {

    private final MailAuthCodeRepository mailAuthCodeRepository;

    public MailAuthCode findByAuthId(String authId) {
        return mailAuthCodeRepository.findById(authId)
                .orElseThrow(AuthCodeNotFoundException::new);
    }

    public void save(MailAuthCode mailAuthCode) {
        mailAuthCodeRepository.save(mailAuthCode);
    }

}
