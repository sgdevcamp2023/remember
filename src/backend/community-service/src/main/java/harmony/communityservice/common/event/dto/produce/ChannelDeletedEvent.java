package harmony.communityservice.common.event.dto.produce;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelDeletedEvent extends ProduceEvent {

    private Long channelId;

    @Builder
    public ChannelDeletedEvent(Long guildId, String type, Long channelId) {
        super(guildId, type);
        this.channelId = channelId;
    }
}
