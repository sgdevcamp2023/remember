package harmony.communityservice.room.service.command;

import harmony.communityservice.room.dto.DeleteRoomRequest;
import harmony.communityservice.room.dto.RegisterRoomRequest;

public interface RoomCommandService {

    void register(RegisterRoomRequest registerRoomRequest);

    void delete(DeleteRoomRequest deleteRoomRequest);
}
