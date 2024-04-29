package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.dto.SearchRoomsAndGuildsResponse;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import harmony.communityservice.room.service.query.RoomQueryService;
import harmony.communityservice.user.domain.UserId;
import java.util.List;
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
        List<Long> guildIds = guildReadQueryService.searchGuildIdsByUserId(UserId.make(userId))
                .stream()
                .map(GuildId::getId)
                .toList();
        List<Long> roomIds = roomQueryService.searchRoomIdsByUserId(userId);
        return new SearchRoomsAndGuildsResponse(roomIds, guildIds);
    }
}
