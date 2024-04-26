package harmony.communityservice.room.repository.query.jpa;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaRoomQueryRepository extends JpaRepository<Room, RoomId> {
    @Query("select r from Room r join r.roomUsers ru where ru.userId = :userId")
    List<Room> findRoomsByUserIdsContains(@Param("userId") UserId userId);
}
