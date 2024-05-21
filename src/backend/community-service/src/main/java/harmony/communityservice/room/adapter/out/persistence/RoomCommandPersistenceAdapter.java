package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.room.application.port.out.DeleteRoomPort;
import harmony.communityservice.room.application.port.out.RegisterRoomPort;
import harmony.communityservice.room.domain.Room;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class RoomCommandPersistenceAdapter implements RegisterRoomPort, DeleteRoomPort {

    private final RoomCommandRepository roomCommandRepository;

    @Override
    public void register(Room room) {
        RoomEntity roomEntity = RoomEntityMapper.convert(room);
        roomCommandRepository.save(roomEntity);
    }

    @Override
    public void delete(UserId firstUserId, UserId secondUserId) {
        UserIdJpaVO firstUserIdJpaVO = UserIdJpaVO.make(firstUserId.getId());
        UserIdJpaVO secondUserIdJpaVO = UserIdJpaVO.make(secondUserId.getId());
        roomCommandRepository.deleteRoomByUserIds(firstUserIdJpaVO, secondUserIdJpaVO);
    }
}
