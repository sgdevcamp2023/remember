package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domainentity.AggregateRootEntity;
import harmony.communityservice.common.generic.ProfileInfoJpaVO;
import harmony.communityservice.room.adapter.out.persistence.RoomIdJpaVO.RoomIdJavaType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomEntity extends AggregateRootEntity<RoomEntity, RoomIdJpaVO> {


    @Id
    @Column(name = "room_id")
    @JavaType(RoomIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoomIdJpaVO roomIdJpaVO;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private final List<RoomUserEntity> roomUserEntities = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "room_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "room_profile"))
    })
    private ProfileInfoJpaVO roomInfo;

    @Version
    private Long version;

    @Builder
    public RoomEntity(String name, String profile, List<RoomUserEntity> roomUserEntities) {
        this.roomInfo = makeRoomInfo(name, profile);
        this.roomUserEntities.addAll(roomUserEntities);
    }

    private ProfileInfoJpaVO makeRoomInfo(String name, String profile) {
        return ProfileInfoJpaVO.make(name, profile);
    }

    @Override
    public RoomIdJpaVO getId() {
        return roomIdJpaVO;
    }
}
