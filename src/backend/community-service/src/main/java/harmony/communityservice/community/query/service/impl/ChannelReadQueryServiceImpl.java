package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.ChannelRead;
import harmony.communityservice.community.query.repository.ChannelReadQueryRepository;
import harmony.communityservice.community.query.service.ChannelReadQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelReadQueryServiceImpl implements ChannelReadQueryService {

    private final ChannelReadQueryRepository channelReadQueryRepository;
    private final UserReadQueryService userReadQueryService;
    @Override
    public List<ChannelRead> findChannelsByGuildId(Long guildId, Long userId) {
        userReadQueryService.existsUserIdAndGuildId(userId, guildId);
        return channelReadQueryRepository.findChannelReadsByGuildId(guildId);
    }
}
