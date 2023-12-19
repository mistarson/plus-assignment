package plus.plusassignment.api.mail.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.plusassignment.api.mail.service.MailService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MailController {

    private final MailService mailService;

    @GetMapping("/emails/vertification-requests")
    public ResponseEntity<?> sendVertificationMail(@RequestParam String email) {

        mailService.sendEmail(email);

        return ResponseEntity.ok("전송 성공");
    }
}
