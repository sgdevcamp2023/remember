package harmony.communityservice.common.service.impl;

import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import harmony.communityservice.guild.domain.GuildRead;
import harmony.communityservice.common.dto.SearchRoomsAndGuildsResponse;
import harmony.communityservice.room.service.query.RoomQueryService;
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
