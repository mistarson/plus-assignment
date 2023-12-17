package plus.plusassignment.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NormalUser extends User {

    @Column(length = 100, nullable = false)
    private String password;

    public void setIdForRegister(String id) {
        super.setIdForRegister(id);
    }

}
