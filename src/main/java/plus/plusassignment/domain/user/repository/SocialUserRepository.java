package plus.plusassignment.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import plus.plusassignment.domain.user.entity.SocialUser;

public interface SocialUserRepository extends JpaRepository<SocialUser, String> {

    @Query(value = "select nextval('seq_user')", nativeQuery = true)
    Long getIdFromSeqUser();

    Optional<SocialUser> findByEmail(String email);

}
