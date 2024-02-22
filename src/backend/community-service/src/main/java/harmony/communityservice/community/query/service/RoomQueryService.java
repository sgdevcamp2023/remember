package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Room;
import harmony.communityservice.community.query.dto.RoomsResponseDto;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoomQueryService {
    RoomsResponseDto searchRoom(long userId);

    Map<Long, ?> findByRoomId(long roomId);
}
