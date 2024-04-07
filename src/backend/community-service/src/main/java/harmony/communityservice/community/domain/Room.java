package harmony.communityservice.community.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @Column(name = "room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "room_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "room_profile"))
    })
    private ProfileInfo roomInfo;

    @Embedded
    private CreationTime creationTime;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomUser> roomUsers = new ArrayList<>();

    @Builder
    public Room(String name, String profile) {
        this.roomInfo = makeRoomInfo(name, profile);
        this.creationTime = new CreationTime();
    }

    private ProfileInfo makeRoomInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }

    public List<User> makeUsers() {
        return roomUsers
                .stream()
                .map(RoomUser::getUser)
                .toList();
    }

}
