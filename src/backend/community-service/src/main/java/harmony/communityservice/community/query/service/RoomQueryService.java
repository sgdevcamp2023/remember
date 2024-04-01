package harmony.communityservice.community.query.service;

import harmony.communityservice.community.query.dto.SearchRoomsResponse;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoomQueryService {

    SearchRoomsResponse searchList(long userId);

    Map<Long, ?> searchUserStatesInRoom(long roomId);
}
