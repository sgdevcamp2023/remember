package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.DomainEntity;
import harmony.communityservice.room.domain.RoomUserId.RoomUserIdJavaType;
import harmony.communityservice.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "room_user",indexes = @Index(name = "idx__user_id", columnList = "user_id"))
public class RoomUser extends DomainEntity<RoomUser, RoomUserId> {

    @Id
    @Column(name = "room_user_id")
    @JavaType(RoomUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoomUserId roomUserId;

    @Getter
    @Embedded
    @Column(name = "user_id")
    private UserId userId;

    private RoomUser(UserId userId) {
        this.userId = userId;
    }

    @Override
    public RoomUserId getId() {
        return roomUserId;
    }

    public static RoomUser make(UserId userId) {
        return new RoomUser(userId);
    }
}
