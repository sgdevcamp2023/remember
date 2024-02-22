package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.dto.RoomGuildResponseDto;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import harmony.communityservice.community.query.service.UserQueryService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
        Map<Long, GuildRead> findGuildReads = guildReadQueryService.findGuildReadsByUserId(userId);
        List<Long> guildIds = new ArrayList<>(findGuildReads.keySet());

        return new RoomGuildResponseDto(roomIds, guildIds);

    }
}
