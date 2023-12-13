package plus.plusassignment.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import plus.plusassignment.domain.user.entity.NormalUser;

public interface NormalUserRepository extends JpaRepository<NormalUser, String> {

    @Query(value = "select nextval('seq_user')", nativeQuery = true)
    Long getIdFromSeqUser();

    Optional<NormalUser> findByUsername(String username);

    Optional<NormalUser> findByEmail(String email);

}
