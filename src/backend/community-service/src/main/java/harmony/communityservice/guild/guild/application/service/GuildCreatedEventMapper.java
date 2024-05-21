package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.event.dto.produce.GuildCreatedEvent;
import harmony.communityservice.guild.guild.domain.Guild;

class GuildCreatedEventMapper {

    static GuildCreatedEvent convert(Guild targetGuild) {
        return GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(targetGuild.getGuildId().getId())
                .name(targetGuild.getProfileInfo().getName())
                .profile(targetGuild.getProfileInfo().getProfile())
                .build();
    }

    static GuildCreatedEvent convert(Guild targetGuild, Long guildId) {
        return GuildCreatedEvent.builder()
                .type("CREATE-GUILD")
                .guildId(guildId)
                .name(targetGuild.getProfileInfo().getName())
                .profile(targetGuild.getProfileInfo().getProfile())
                .build();
    }
}
