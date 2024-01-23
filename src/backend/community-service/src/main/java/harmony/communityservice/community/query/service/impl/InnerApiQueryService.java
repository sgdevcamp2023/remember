package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.dto.RoomGuildResponseDto;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InnerApiQueryService {

    private final UserQueryService userQueryService;
    private final GuildReadQueryService guildReadQueryService;

    public RoomGuildResponseDto search(long userId) {
        List<Long> roomIds = userQueryService.findUser(userId)
                .getRoomUsers()
                .stream()
                .map(roomUser -> roomUser.getRoom().getRoomId()).toList();
        List<Long> guildIds = guildReadQueryService.findGuildReadsByUserId(userId)
                .stream()
                .map(GuildRead::getGuildId).toList();
        return new RoomGuildResponseDto(roomIds, guildIds);

    }
}
