package harmony.communityservice.guild.guild.service.query.impl;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.repository.query.GuildReadQueryRepository;
import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import harmony.communityservice.user.domain.UserId;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildReadQueryServiceImpl implements GuildReadQueryService {

    private final GuildReadQueryRepository guildReadQueryRepository;

    @Override
    public Map<Long, GuildRead> searchMapByUserId(long userId) {
        Map<Long, GuildRead> guildReads = new HashMap<>();
        for (GuildRead guildRead : guildReadQueryRepository.findGuildsByUserId(UserId.make(userId))) {
            guildReads.put(guildRead.getGuildId().getId(), guildRead);
        }
        return guildReads;
    }

}
