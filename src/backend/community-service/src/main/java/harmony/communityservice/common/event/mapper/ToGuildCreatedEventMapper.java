package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.GuildCreatedEvent;
import harmony.communityservice.guild.domain.GuildRead;

public class ToGuildCreatedEventMapper {

    public static GuildCreatedEvent convert(GuildRead guildRead) {
        return GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(guildRead.getGuildId())
                .guildReadId(guildRead.getGuildReadId())
                .name(guildRead.getGuildInfo().getName())
                .profile(guildRead.getGuildInfo().getProfile())
                .build();
    }
}
