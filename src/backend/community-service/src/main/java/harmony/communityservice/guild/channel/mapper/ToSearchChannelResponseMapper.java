package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.channel.domain.Channel;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;

public class ToSearchChannelResponseMapper {

    public static SearchChannelResponse convert(Channel channel, Long channelId, Long guildId) {
        return SearchChannelResponse.builder()
                .channelId(channelId)
                .channelName(channel.getName())
                .channelType(channel.getType())
                .categoryId(channel.getCategoryId())
                .guildId(guildId)
                .build();
    }
}
