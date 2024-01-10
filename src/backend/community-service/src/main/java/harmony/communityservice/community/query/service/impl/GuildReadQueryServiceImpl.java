package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.repository.GuildReadQueryRepository;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadQueryServiceImpl implements GuildReadQueryService {

    private final GuildReadQueryRepository guildReadQueryRepository;

    @Override
    public List<GuildRead> findGuildReadsByUserId(long userId) {
        return guildReadQueryRepository.findGuildsByUserId(userId);
    }

}
