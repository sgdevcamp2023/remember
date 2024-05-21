package harmony.communityservice.guild.channel.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsCommand;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsQuery;
import harmony.communityservice.guild.channel.application.port.out.LoadChannelsPort;
import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelResponse;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ChannelQueryService implements LoadChannelsQuery {

    private final LoadChannelsPort loadChannelsPort;


    @Override
    @AuthorizeGuildMember
    public Map<Long, LoadChannelResponse> loadChannels(LoadChannelsCommand loadChannelsCommand) {
        Map<Long, LoadChannelResponse> result = new HashMap<>();
        List<Channel> channels = loadChannelsPort.loadChannels(GuildId.make(loadChannelsCommand.guildId()));
        channels.forEach(channel -> {
            LoadChannelResponse searchChannelResponse = LoadChannelResponseMapper.convert(channel);
            result.put(channel.getChannelId().getId(), searchChannelResponse);
        });
        return result;
    }

}
