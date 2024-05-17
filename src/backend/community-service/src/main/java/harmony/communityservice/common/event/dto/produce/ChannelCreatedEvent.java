package harmony.communityservice.common.event.dto.produce;

import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelTypeJpaEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChannelCreatedEvent extends ProduceEvent {

    private ChannelTypeJpaEnum channelType;
    private String channelName;
    private Long channelId;
    private Long categoryId;

    @Builder
    public ChannelCreatedEvent(Long guildId, String type, Long categoryId, Long channelId, String channelName,
                               ChannelTypeJpaEnum channelType) {
        super(guildId, type);
        this.categoryId = categoryId;
        this.channelId = channelId;
        this.channelName = channelName;
        this.channelType = channelType;
    }
}
