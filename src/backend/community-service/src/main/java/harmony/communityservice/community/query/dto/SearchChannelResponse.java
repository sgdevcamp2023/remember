package harmony.communityservice.community.query.dto;

import harmony.communityservice.community.domain.ChannelType;
import lombok.Builder;

@Builder(toBuilder = true)
public record SearchChannelResponse(Long channelId, Long guildId, Long categoryId, String channelName,
                                    ChannelType channelType) {
}
