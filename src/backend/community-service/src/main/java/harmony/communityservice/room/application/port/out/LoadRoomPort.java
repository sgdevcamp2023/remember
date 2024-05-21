package harmony.communityservice.room.application.port.out;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.room.domain.Room.RoomId;

public interface LoadRoomPort {

    Room loadByRoomId(RoomId roomId);
}
