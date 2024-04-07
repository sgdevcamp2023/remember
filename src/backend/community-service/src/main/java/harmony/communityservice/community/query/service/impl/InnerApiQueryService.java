package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.dto.SearchRoomsAndGuildsResponse;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import harmony.communityservice.community.query.service.RoomQueryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InnerApiQueryService {

    private final RoomQueryService roomQueryService;
    private final GuildReadQueryService guildReadQueryService;

    public SearchRoomsAndGuildsResponse search(long userId) {
        Map<Long, GuildRead> targetGuildReads = guildReadQueryService.searchMapByUserId(userId);
        List<Long> guildIds = new ArrayList<>(targetGuildReads.keySet());
        List<Long> roomIds = roomQueryService.searchRoomIdsByUserId(userId);
        return new SearchRoomsAndGuildsResponse(roomIds, guildIds);
    }
}
