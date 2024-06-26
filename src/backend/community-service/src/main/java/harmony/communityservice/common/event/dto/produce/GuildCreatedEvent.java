package harmony.communityservice.common.event.dto.produce;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class GuildCreatedEvent extends ProduceEvent {

    private String name;
    private String profile;

    @Builder
    public GuildCreatedEvent(Long guildId, String type, String name, String profile) {
        super(guildId, type);
        this.name = name;
        this.profile = profile;
    }
}
