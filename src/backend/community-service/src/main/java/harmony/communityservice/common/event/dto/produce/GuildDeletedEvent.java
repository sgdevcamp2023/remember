package harmony.communityservice.common.event.dto.produce;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildDeletedEvent extends ProduceEvent {

    @Builder
    public GuildDeletedEvent(Long guildId, String type) {
        super(guildId, type);
    }
}
