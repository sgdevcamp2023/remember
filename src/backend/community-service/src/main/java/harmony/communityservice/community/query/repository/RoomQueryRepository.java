package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RoomQueryRepository {
    Optional<Room> findByRoomId(long roomId);
    List<Room> findRoomsByUserIdsContains(Long userId);
}
