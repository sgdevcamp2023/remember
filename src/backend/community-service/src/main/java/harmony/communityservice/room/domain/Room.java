package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import harmony.communityservice.room.domain.RoomId.RoomIdJavaType;
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
public class Room extends AggregateRoot<Room, RoomId> {

    @Id
    @Column(name = "room_id")
    @JavaType(RoomIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private RoomId roomId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "room_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "room_profile"))
    })
    private ProfileInfo roomInfo;

    @Embedded
    private CreationTime creationTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private final List<RoomUser> roomUsers = new ArrayList<>();

    @Version
    private Long version;

    @Builder
    public Room(String name, String profile, List<RoomUser> roomUsers) {
        this.roomInfo = makeRoomInfo(name, profile);
        this.creationTime = new CreationTime();
        this.roomUsers.addAll(roomUsers);
    }

    private ProfileInfo makeRoomInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }

    @Override
    public RoomId getId() {
        return roomId;
    }
}
