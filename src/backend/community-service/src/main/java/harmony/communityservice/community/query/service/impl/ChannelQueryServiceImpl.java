package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.mapper.ToSearchChannelResponseMapper;
import harmony.communityservice.community.query.dto.SearchChannelResponse;
import harmony.communityservice.community.query.repository.ChannelQueryRepository;
import harmony.communityservice.community.query.service.ChannelQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelQueryServiceImpl implements ChannelQueryService {

    private final ChannelQueryRepository channelQueryRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public Channel searchByChannelId(long channelId) {
        return channelQueryRepository.findByChannelId(channelId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public Map<Long, SearchChannelResponse> searchMapByGuildId(Long guildId, Long userId) {
        userReadQueryService.existsByUserIdAndGuildId(new VerifyGuildMemberRequest(userId, guildId));
        return findChannels(guildId);
    }

    private Map<Long, SearchChannelResponse> findChannels(Long guildId) {
        Map<Long, SearchChannelResponse> channelReads = new HashMap<>();
        for (Channel channel: channelQueryRepository.findChannelsByGuildId(guildId)) {
            SearchChannelResponse searchChannelResponse = ToSearchChannelResponseMapper.convert(channel);
            channelReads.put(channel.getChannelId(), searchChannelResponse);
        }
        return channelReads;
    }
}
