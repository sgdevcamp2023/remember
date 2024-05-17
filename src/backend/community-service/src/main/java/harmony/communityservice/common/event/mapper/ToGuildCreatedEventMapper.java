package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildEntity;
import harmony.communityservice.guild.guild.domain.Guild;

public class ToGuildCreatedEventMapper {

    public static GuildCreatedEvent convert(Guild targetGuild) {
        return GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(targetGuild.getGuildId().getId())
                .name(targetGuild.getProfileInfo().getName())
                .profile(targetGuild.getProfileInfo().getProfile())
                .build();
    }
}
