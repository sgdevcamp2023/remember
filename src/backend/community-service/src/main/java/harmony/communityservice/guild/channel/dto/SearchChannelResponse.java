package harmony.communityservice.guild.channel.dto;

import harmony.communityservice.guild.domain.ChannelType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchChannelResponse(Integer channelId, Long guildId, Integer categoryId, String channelName,
                                    ChannelType channelType) {
}
