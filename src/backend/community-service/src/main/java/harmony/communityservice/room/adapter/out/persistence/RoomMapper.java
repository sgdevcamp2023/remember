package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;

class RoomMapper {

    static Room convert(RoomEntity roomEntity) {
        return Room.builder()
                .name(roomEntity.getRoomInfo().getName())
                .profile(roomEntity.getRoomInfo().getProfile())
                .roomId(RoomId.make(roomEntity.getRoomIdJpaVO().getId()))
                .build();
    }
}
