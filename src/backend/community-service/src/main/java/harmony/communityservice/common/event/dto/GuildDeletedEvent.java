package harmony.communityservice.common.event.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildDeletedEvent extends KafkaEvent {

    @Builder
    public GuildDeletedEvent(Long guildId, String type) {
        super(guildId, type);
    }
}
