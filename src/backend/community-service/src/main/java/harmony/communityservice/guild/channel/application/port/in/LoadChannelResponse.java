package harmony.communityservice.guild.channel.application.port.in;

import harmony.communityservice.guild.channel.domain.ChannelType;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoadChannelResponse(Long channelId, Long guildId, Long categoryId, String channelName,
                                  ChannelType channelType) {
}
