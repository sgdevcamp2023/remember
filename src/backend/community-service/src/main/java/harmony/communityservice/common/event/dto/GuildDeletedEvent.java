package harmony.communityservice.common.event.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildDeletedEvent extends Event {

    @Builder
    public GuildDeletedEvent(Long guildId, String type) {
        super(guildId, type);
    }
}
