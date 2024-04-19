package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.GuildDeletedEvent;

public class ToGuildDeletedEventMapper {

    public static GuildDeletedEvent convert(Long guildId) {
        return GuildDeletedEvent.builder()
                .type("DELETE-GUILD")
                .guildId(guildId)
                .build();
    }

}
