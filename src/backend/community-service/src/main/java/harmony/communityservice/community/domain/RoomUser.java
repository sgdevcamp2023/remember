package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "room_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomUser {

    @Id
    @Column(name = "room_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private RoomUser(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public static RoomUser make(Room room, User user) {
        return new RoomUser(room, user);
    }
}
