package harmony.communityservice.guild.channel.dto;

import harmony.communityservice.guild.domain.ChannelType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchChannelResponse(Long channelId, Long guildId, Long categoryId, String channelName,
                                    ChannelType channelType) {
}
