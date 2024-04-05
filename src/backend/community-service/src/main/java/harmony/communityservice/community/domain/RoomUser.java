package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "room_user", indexes = @Index(name = "idx__roomId__userId", columnList = "room_id, user_id"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomUser {

    @Id
    @Column(name = "room_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private RoomUser(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public static RoomUser make(Room room, User user) {
        return new RoomUser(room, user);
    }

    public boolean sameRoomId(long roomId) {
        return room.getRoomId().equals(roomId);
    }
}
