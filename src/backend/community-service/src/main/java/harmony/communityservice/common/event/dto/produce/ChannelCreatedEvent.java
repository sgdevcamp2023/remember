package harmony.communityservice.common.event.dto.produce;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelCreatedEvent extends ProduceEvent {

    private String channelType;
    private String channelName;
    private Long channelId;
    private Long categoryId;

    @Builder
    public ChannelCreatedEvent(Long guildId, String type, Long categoryId, Long channelId, String channelName,
                               String channelType) {
        super(guildId, type);
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
    }
}
