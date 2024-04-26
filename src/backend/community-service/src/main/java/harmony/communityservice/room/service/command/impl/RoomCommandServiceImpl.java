package harmony.communityservice.room.service.command.impl;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.dto.DeleteRoomRequest;
import harmony.communityservice.room.dto.RegisterRoomRequest;
import harmony.communityservice.room.mapper.ToRoomMapper;
import harmony.communityservice.room.repository.command.RoomCommandRepository;
import harmony.communityservice.room.service.command.RoomCommandService;
import harmony.communityservice.user.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RoomCommandServiceImpl implements RoomCommandService {

    private final RoomCommandRepository roomCommandRepository;

    @Override
    public void register(RegisterRoomRequest registerRoomRequest) {
        Room room = ToRoomMapper.convert(registerRoomRequest);
        roomCommandRepository.save(room);
    }

    @Override
    public void delete(DeleteRoomRequest deleteRoomRequest) {
        UserId firstUserId = UserId.make(deleteRoomRequest.firstUser());
        UserId secondUserId = UserId.make(deleteRoomRequest.secondUser());
        roomCommandRepository.deleteRoomByUserIds(firstUserId, secondUserId);
    }

}
