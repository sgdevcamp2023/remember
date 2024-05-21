package harmony.communityservice.room.application.port.out;

import harmony.communityservice.room.domain.Room;

public interface RegisterRoomPort {

    void register(Room room);
}
