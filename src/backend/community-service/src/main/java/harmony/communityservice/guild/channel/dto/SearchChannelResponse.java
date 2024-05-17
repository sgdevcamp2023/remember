package harmony.communityservice.guild.channel.dto;

import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchChannelResponse(Long channelId, Long guildId, Long categoryId, String channelName,
                                    ChannelTypeJpaEnum channelType) {
}
