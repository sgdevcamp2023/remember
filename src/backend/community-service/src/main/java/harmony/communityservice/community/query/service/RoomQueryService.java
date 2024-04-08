package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.query.dto.SearchRoomsResponse;
import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public interface RoomQueryService {

    SearchRoomsResponse searchList(long userId);

    Map<Long, ?> searchUserStatesInRoom(long roomId);

    List<Long> searchRoomIdsByUserId(long userId);
}
