package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;

public class ToGuildCreatedEventMapper {

    public static GuildCreatedEvent convert(RegisterGuildReadEvent event) {
        return GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(event.guildId())
                .name(event.name())
                .profile(event.profile())
                .build();
    }
}
