package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.query.repository.ChannelQueryRepository;
import harmony.communityservice.community.query.service.ChannelQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelQueryServiceImpl implements ChannelQueryService {

    private final ChannelQueryRepository channelQueryRepository;

    @Override
    public Channel findChannelByChannelId(long channelId) {
        return channelQueryRepository.findByChannelId(channelId).orElseThrow(NotFoundDataException::new);
    }
}
