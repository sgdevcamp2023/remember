package harmony.communityservice.user.domain;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserId extends ValueObject<UserId> {

    @Column(name = "user_id")
    private Long id;

    public static UserId make(Long userId) {
        return new UserId(userId);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { id };
    }
}
