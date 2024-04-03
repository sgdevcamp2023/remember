package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.community.domain.ChannelRead;
import harmony.communityservice.community.query.repository.ChannelReadQueryRepository;
import harmony.communityservice.community.query.service.ChannelReadQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChannelReadQueryServiceImpl implements ChannelReadQueryService {

    private final ChannelReadQueryRepository channelReadQueryRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public Map<Long, ChannelRead> searchMapByGuildId(Long guildId, Long userId) {
        userReadQueryService.existsByUserIdAndGuildId(new VerifyGuildMemberRequest(userId, guildId));
        return findChannelReads(guildId);
    }

    private Map<Long, ChannelRead> findChannelReads(Long guildId) {
        Map<Long, ChannelRead> channelReads = new HashMap<>();
        for (ChannelRead channelRead : channelReadQueryRepository.findChannelReadsByGuildId(guildId)) {
            channelReads.put(channelRead.getChannelReadId(), channelRead);
        }
        return channelReads;
    }
}
