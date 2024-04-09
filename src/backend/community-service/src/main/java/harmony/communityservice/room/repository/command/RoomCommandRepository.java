package harmony.communityservice.room.repository.command;

import harmony.communityservice.room.domain.Room;

public interface RoomCommandRepository {

    void save(Room room);

    void deleteRoomByUserIds(Long first, Long second);
}
