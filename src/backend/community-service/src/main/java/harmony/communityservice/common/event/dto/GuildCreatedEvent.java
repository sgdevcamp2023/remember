package harmony.communityservice.common.event.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GuildCreatedEvent extends KafkaEvent {

    private Long guildReadId;
    private String name;
    private String profile;

    @Builder
    public GuildCreatedEvent(Long guildId, String type, Long guildReadId, String name, String profile) {
        super(guildId, type);
        this.guildReadId = guildReadId;
        this.name = name;
        this.profile = profile;
    }
}
