package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.event.dto.produce.GuildDeletedEvent;

public class GuildDeletedEventMapper {

    public static GuildDeletedEvent convert(Long guildId) {
        return GuildDeletedEvent.builder()
                .type("DELETE-GUILD")
                .guildId(guildId)
                .build();
    }

}
