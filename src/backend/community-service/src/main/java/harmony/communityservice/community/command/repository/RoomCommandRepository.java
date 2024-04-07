package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Room;

public interface RoomCommandRepository {

    void save(Room room);

    void deleteRoomByUserIds(Long first, Long second);
}
