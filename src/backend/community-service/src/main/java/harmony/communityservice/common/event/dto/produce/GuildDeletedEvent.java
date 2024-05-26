package harmony.communityservice.common.event.dto.produce;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GuildDeletedEvent extends ProduceEvent {

    @Builder
    public GuildDeletedEvent(Long guildId, String type) {
        super(guildId, type);
    }
}
