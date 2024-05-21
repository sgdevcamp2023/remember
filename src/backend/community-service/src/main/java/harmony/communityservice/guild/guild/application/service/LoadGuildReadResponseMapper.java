package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.adapter.in.web.SearchGuildReadResponse;

public class LoadGuildReadResponseMapper {

    public static SearchGuildReadResponse convert(GuildRead guildRead) {
        return SearchGuildReadResponse.builder()
                .guildReadId(guildRead.getGuildReadId().getId())
                .guildId(guildRead.getGuildId().getId())
                .userId(guildRead.getUserId().getId())
                .name(guildRead.getProfileInfo().getName())
                .profile(guildRead.getProfileInfo().getProfile())
                .build();
    }
}
