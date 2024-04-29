package harmony.communityservice.guild.guild.service.query.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.dto.SearchGuildReadResponse;
import harmony.communityservice.guild.guild.mapper.ToSearchGuildReadResponseMapper;
import harmony.communityservice.guild.guild.repository.query.GuildReadQueryRepository;
import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import harmony.communityservice.user.domain.UserId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildReadQueryServiceImpl implements GuildReadQueryService {

    private final GuildReadQueryRepository guildReadQueryRepository;

    @Override
    public Map<Long, SearchGuildReadResponse> searchMapByUserId(long userId) {
        Map<Long, SearchGuildReadResponse> guildReads = new HashMap<>();
        for (GuildRead guildRead : guildReadQueryRepository.findGuildsByUserId(UserId.make(userId))) {
            guildReads.put(guildRead.getGuildId().getId(), ToSearchGuildReadResponseMapper.convert(guildRead));
        }
        return guildReads;
    }

    @Override
    public List<GuildId> searchGuildIdsByUserId(UserId userId) {
        return guildReadQueryRepository.findGuildIdsByUserId(userId);
    }
}
