package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.List;

class RoomEntityMapper {

    static RoomEntity convert(Room room) {
        List<RoomUserEntity> roomUserEntities = room.getRoomUsers()
                .stream()
                .map(roomUser -> UserIdJpaVO.make(roomUser.getUserId().getId()))
                .map(RoomUserEntity::make)
                .toList();

        return RoomEntity.builder()
                .profile(room.getProfileInfo().getProfile())
                .name(room.getProfileInfo().getName())
                .roomUserEntities(roomUserEntities)
                .build();
    }
}
