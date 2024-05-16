package harmony.communityservice.room.application.port.out;

import harmony.communityservice.room.domain.Room;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

public interface LoadRoomsPort {

    List<Room> loadRooms(UserId userId);
}
