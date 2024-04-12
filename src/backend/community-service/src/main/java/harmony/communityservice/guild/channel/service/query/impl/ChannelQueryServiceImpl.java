package harmony.communityservice.guild.channel.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;
import harmony.communityservice.guild.channel.mapper.ToSearchChannelResponseMapper;
import harmony.communityservice.guild.channel.repository.query.ChannelQueryRepository;
import harmony.communityservice.guild.channel.service.query.ChannelQueryService;
import harmony.communityservice.guild.domain.Channel;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.repository.query.GuildQueryRepository;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
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
    public Map<Integer, SearchChannelResponse> searchMapByGuildId(SearchParameterMapperRequest parameterMapperRequest) {
        return findChannels(parameterMapperRequest.guildId());
    }

    private Map<Integer, SearchChannelResponse> findChannels(Long guildId) {
        Map<Integer, SearchChannelResponse> channelReads = new HashMap<>();
        List<Channel> channels = channelQueryRepository.findListByGuildId(guildId);
        channels.forEach(channel -> {
            SearchChannelResponse searchChannelResponse = ToSearchChannelResponseMapper.convert(channel,
                    channel.getChannelId(), guildId);
            channelReads.put(channels.indexOf(channel), searchChannelResponse);
        });
        return channelReads;
    }
}
