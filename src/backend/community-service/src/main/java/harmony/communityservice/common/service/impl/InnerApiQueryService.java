package harmony.communityservice.common.service.impl;

import harmony.communityservice.common.dto.SearchRoomsAndGuildsResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildIdsQuery;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.room.application.port.in.LoadRoomIdsQuery;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InnerApiQueryService {

    private final LoadRoomIdsQuery loadRoomIdsQuery;
    private final LoadGuildIdsQuery loadGuildIdsQuery;

    public SearchRoomsAndGuildsResponse search(long userId) {
        List<Long> guildIds = loadGuildIdsQuery.loadGuildIdsByUserId(UserId.make(userId))
                .stream()
                .map(GuildId::getId)
                .toList();
        List<Long> roomIds = loadRoomIdsQuery.loadRoomIds(userId);
        return new SearchRoomsAndGuildsResponse(roomIds, guildIds);
    }
}
