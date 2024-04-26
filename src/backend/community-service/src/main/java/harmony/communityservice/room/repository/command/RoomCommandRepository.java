package harmony.communityservice.room.repository.command;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.RoomId;
import harmony.communityservice.user.domain.UserId;
import java.util.Optional;

public interface RoomCommandRepository {

    void save(Room room);

    Optional<Room> findById(RoomId roomId);

    void deleteRoomByUserIds(UserId first, UserId second);
}
