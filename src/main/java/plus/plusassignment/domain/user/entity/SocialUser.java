package plus.plusassignment.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import plus.plusassignment.domain.user.constant.SocialType;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialUser extends User {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    public void setIdForRegister(String id) {
        super.setIdForRegister(id);
    }

}
