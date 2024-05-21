package harmony.communityservice.room.application.port.in;

import java.util.List;

public interface LoadRoomIdsQuery {

    List<Long> loadRoomIds(Long userId);
}
