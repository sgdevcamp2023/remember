package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteRoomRequest;
import harmony.communityservice.community.command.dto.RegisterRoomRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RoomCommandService {

    void register(RegisterRoomRequest registerRoomRequest);

    void delete(DeleteRoomRequest deleteRoomRequest);
}
