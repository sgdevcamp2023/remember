package harmony.communityservice.room.repository.query;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.List;
import java.util.Optional;

public interface RoomQueryRepository {
    Optional<Room> findByRoomId(RoomId roomId);
    List<Room> findRoomsByUserIdsContains(UserId userId);
}
