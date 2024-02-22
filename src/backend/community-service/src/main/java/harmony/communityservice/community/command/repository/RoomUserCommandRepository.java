package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.RoomUser;

public interface RoomUserCommandRepository {
    void save(RoomUser roomUser);

    void deleteByRoomUser(RoomUser roomUser);
}
