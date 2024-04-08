package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.DeleteRoomRequest;
import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import harmony.communityservice.community.command.repository.RoomCommandRepository;
import harmony.communityservice.community.command.service.RoomCommandService;
import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.mapper.ToRoomMapper;
import java.util.HashSet;
import java.util.Set;
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
        roomCommandRepository.deleteRoomByUserIds(deleteRoomRequest.firstUser(), deleteRoomRequest.secondUser());
    }

}
