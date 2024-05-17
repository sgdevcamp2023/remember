package harmony.communityservice.guild.channel.mapper;

import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelEntity;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;

public class ToSearchChannelResponseMapper {

    public static SearchChannelResponse convert(ChannelEntity channel, Long channelId, Long guildId) {
        return SearchChannelResponse.builder()
                .channelId(channelId)
                .channelName(channel.getName())
                .channelType(channel.getChannelType())
                .categoryId(channel.getCategoryId().getId())
                .guildId(guildId)
                .build();
    }
}
