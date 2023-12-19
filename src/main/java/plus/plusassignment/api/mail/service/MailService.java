package plus.plusassignment.api.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.user.redis.MailAuthCode;
import plus.plusassignment.domain.user.redis.MailAuthCodeService;
import plus.plusassignment.global.utils.generator.NumberGenerator;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    private final NumberGenerator numberGenerator;

    private final MailAuthCodeService mailAuthCodeService;

    private static final String MAIL_TITLE = "PLUS-ASSIGNMENT 이메일 인증 번호";

    public void sendEmail(String toEmail) {

        String authCode = numberGenerator.numberGenerate();
        SimpleMailMessage emailForm = createEmailForm(toEmail, authCode);

        emailSender.send(emailForm);
        mailAuthCodeService.save(MailAuthCode.createMailAuthCode(toEmail, authCode));
    }

    private SimpleMailMessage createEmailForm(String toEmail, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(MailService.MAIL_TITLE);
        message.setText(text);

        return message;
    }
}
