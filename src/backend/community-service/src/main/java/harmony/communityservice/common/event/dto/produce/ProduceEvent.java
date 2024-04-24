package harmony.communityservice.common.event.dto.produce;

import lombok.Getter;

@Getter
public abstract class ProduceEvent {

    private String type;
    private Long guildId;

    public ProduceEvent(Long guildId, String type) {
        this.guildId = guildId;
        this.type = type;
    }
}
