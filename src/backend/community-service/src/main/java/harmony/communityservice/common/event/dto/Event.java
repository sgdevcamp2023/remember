package harmony.communityservice.common.event.dto;

import lombok.Getter;

@Getter
public abstract class Event {

    private String type;
    private Long guildId;

    public Event(Long guildId, String type) {
        this.guildId = guildId;
        this.type = type;
    }
}
