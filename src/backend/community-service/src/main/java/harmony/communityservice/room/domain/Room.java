package harmony.communityservice.room.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.HashSet;
import java.util.Set;
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

    @ElementCollection
    @CollectionTable(name = "room_user",
            joinColumns = @JoinColumn(name = "room_id"))
    private Set<Long> userIds = new HashSet<>();

    @Version
    private Long version;

    @Builder
    public Room(String name, String profile, Set<Long> userIds) {
        this.roomInfo = makeRoomInfo(name, profile);
        this.creationTime = new CreationTime();
        this.userIds = userIds;
    }

    private ProfileInfo makeRoomInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }
}
