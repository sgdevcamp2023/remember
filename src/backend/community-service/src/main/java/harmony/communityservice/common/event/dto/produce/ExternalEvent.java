package harmony.communityservice.common.event.dto.produce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import harmony.communityservice.common.outbox.ExternalEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.channel.domain.ChannelType;
import lombok.Builder;

@Builder(toBuilder = true)
@JsonInclude(Include.NON_NULL)
public record ExternalEvent(SentType sentType,
                            ExternalEventType type,
                            Long guildId,
                            ChannelType channelType,
                            Long channelId,
                            String channelName,
                            Long categoryId,
                            Long guildReadId,
                            String name,
                            String profile) {
}
