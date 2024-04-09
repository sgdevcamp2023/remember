package harmony.communityservice.room.service.query;

import harmony.communityservice.room.dto.SearchRoomsResponse;
import java.util.List;
import java.util.Map;

public interface RoomQueryService {

    SearchRoomsResponse searchList(long userId);

    List<Long> searchRoomIdsByUserId(long userId);

    Map<Long, ?> searchUserStatesInRoom(long roomId);
}
