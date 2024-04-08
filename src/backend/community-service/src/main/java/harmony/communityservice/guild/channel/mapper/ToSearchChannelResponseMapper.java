package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.domain.Channel;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;

public class ToSearchChannelResponseMapper {

    public static SearchChannelResponse convert(Channel channel) {
        return SearchChannelResponse.builder()
                .channelId(channel.getChannelId())
                .channelName(channel.getName())
                .channelType(channel.getType())
                .categoryId(channel.getCategoryId())
                .guildId(channel.getGuildId())
                .build();
    }
}
