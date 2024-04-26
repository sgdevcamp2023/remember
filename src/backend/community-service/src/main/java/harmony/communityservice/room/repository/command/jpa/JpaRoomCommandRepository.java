package harmony.communityservice.room.repository.command.jpa;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.user.domain.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaRoomCommandRepository extends JpaRepository<Room, RoomId> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Room r "
            + "where :first in (select ru.userId from r.roomUsers ru) "
            + "and :second in (select ru.userId from r.roomUsers ru)")
    void deleteRoomByUserIds(@Param("first") UserId first, @Param("second") UserId second);
}
