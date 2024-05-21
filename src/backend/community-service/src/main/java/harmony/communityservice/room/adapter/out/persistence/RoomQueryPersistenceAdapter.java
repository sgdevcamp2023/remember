package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.room.application.port.out.LoadRoomIdsPort;
import harmony.communityservice.room.application.port.out.LoadRoomPort;
import harmony.communityservice.room.application.port.out.LoadRoomsPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class RoomQueryPersistenceAdapter implements LoadRoomsPort, LoadRoomPort, LoadRoomIdsPort {

    private final roomQueryRepository roomQueryRepository;

    @Override
    public List<Room> loadRooms(UserId userId) {
        List<RoomEntity> roomEntities = roomQueryRepository.findRoomsByUserIdsContains(
                UserIdJpaVO.make(userId.getId()));
        return roomEntities.stream().map(RoomMapper::convert).toList();
    }

    @Override
    public Room loadByRoomId(RoomId roomId) {
        RoomEntity roomEntity = roomQueryRepository.findById(RoomIdJpaVO.make(roomId.getId()))
                .orElseThrow(NotFoundDataException::new);
        return RoomMapper.convert(roomEntity);
    }

    @Override
    public List<Long> loadRoomIds(UserId userId) {
        return roomQueryRepository.findRoomIdsByUserIDsContains(
                        UserIdJpaVO.make(userId.getId()))
                .stream()
                .map(RoomIdJpaVO::getId)
                .toList();
    }
}
