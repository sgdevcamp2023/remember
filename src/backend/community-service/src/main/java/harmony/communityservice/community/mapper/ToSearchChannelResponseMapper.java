package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.query.dto.SearchChannelResponse;

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
