package harmony.communityservice.common.event.dto;

import lombok.Getter;

@Getter
public abstract class KafkaEvent {

    private String type;
    private Long guildId;

    public KafkaEvent(Long guildId, String type) {
        this.guildId = guildId;
        this.type = type;
    }
}
