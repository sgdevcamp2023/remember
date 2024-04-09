package harmony.communityservice.room.repository.query;

import harmony.communityservice.room.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RoomQueryRepository {
    Optional<Room> findByRoomId(long roomId);
    List<Room> findRoomsByUserIdsContains(Long userId);
}
