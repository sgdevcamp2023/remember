package harmony.communityservice.room.repository.command;

import harmony.communityservice.room.domain.Room;
import java.util.Optional;

public interface RoomCommandRepository {

    void save(Room room);

    Optional<Room> findById(Long roomId);

    void deleteRoomByUserIds(Long first, Long second);
}
