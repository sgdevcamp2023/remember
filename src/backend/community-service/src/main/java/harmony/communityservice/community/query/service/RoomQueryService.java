package harmony.communityservice.community.query.service;

import harmony.communityservice.community.query.dto.RoomsResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoomQueryService {
    RoomsResponseDto searchRoom(long userId);
}
