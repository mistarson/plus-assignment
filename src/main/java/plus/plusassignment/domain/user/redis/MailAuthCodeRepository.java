package plus.plusassignment.domain.user.redis;

import org.springframework.data.repository.CrudRepository;

public interface MailAuthCodeRepository extends CrudRepository<MailAuthCode, String> {

}
