package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.repository.GuildReadQueryRepository;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadQueryServiceImpl implements GuildReadQueryService {

    private final GuildReadQueryRepository guildReadQueryRepository;

    @Override
    public Map<Long, GuildRead> searchMapByUserId(long userId) {
        Map<Long, GuildRead> guildReads = new HashMap<>();
        for (GuildRead guildRead : guildReadQueryRepository.findGuildsByUserId(userId)) {
            guildReads.put(guildRead.getGuildId(), guildRead);
        }
        return guildReads;
    }

}
