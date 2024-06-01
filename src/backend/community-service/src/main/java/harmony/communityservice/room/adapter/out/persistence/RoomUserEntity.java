package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domainentity.DomainEntity;
import harmony.communityservice.room.adapter.out.persistence.RoomUserIdJpaVO.RoomUserIdJavaType;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
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
@Table(name = "room_user", indexes = @Index(name = "idx__user_id", columnList = "user_id"))
public class RoomUserEntity extends DomainEntity<RoomUserEntity, RoomUserIdJpaVO> {

    @Id
    @Column(name = "room_user_id")
    @JavaType(RoomUserIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoomUserIdJpaVO roomUserIdJpaVO;

    @Embedded
    @Column(name = "user_id")
    private UserIdJpaVO userId;

    private RoomUserEntity(UserIdJpaVO userId) {
        this.userId = userId;
    }

    public static RoomUserEntity make(UserIdJpaVO userId) {
        return new RoomUserEntity(userId);
    }

    @Override
    public RoomUserIdJpaVO getId() {
        return roomUserIdJpaVO;
    }
}
