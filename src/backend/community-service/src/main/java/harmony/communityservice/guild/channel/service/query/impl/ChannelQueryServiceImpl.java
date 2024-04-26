package harmony.communityservice.guild.channel.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;
import harmony.communityservice.guild.channel.mapper.ToSearchChannelResponseMapper;
import harmony.communityservice.guild.channel.repository.query.ChannelQueryRepository;
import harmony.communityservice.guild.channel.service.query.ChannelQueryService;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChannelQueryServiceImpl implements ChannelQueryService {

    private final ChannelQueryRepository channelQueryRepository;

    @Override
    @AuthorizeGuildMember
    public Map<Long, SearchChannelResponse> searchMapByGuildId(SearchParameterMapperRequest parameterMapperRequest) {
        return findChannels(parameterMapperRequest.guildId());
    }

    private Map<Long, SearchChannelResponse> findChannels(Long guildId) {
        Map<Long, SearchChannelResponse> channelReads = new HashMap<>();
        List<Channel> channels = channelQueryRepository.findListByGuildId(GuildId.make(guildId));
        channels.forEach(channel -> {
            SearchChannelResponse searchChannelResponse = ToSearchChannelResponseMapper.convert(channel,
                    channel.getChannelId().getId(), guildId);
            channelReads.put(channel.getChannelId().getId(), searchChannelResponse);
        });
        return channelReads;
    }
}
