package harmony.communityservice.room.application.port.out;

import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

public interface LoadRoomIdsPort {

    List<Long> loadRoomIds(UserId userId);
}
