package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.dto.SearchGuildReadResponse;

public class ToSearchGuildReadResponseMapper {

    public static SearchGuildReadResponse convert(GuildRead guildRead) {
        return SearchGuildReadResponse.builder()
                .guildReadId(guildRead.getGuildReadId().getId())
                .guildId(guildRead.getGuildId().getId())
                .userId(guildRead.getUserId().getId())
                .name(guildRead.getGuildInfo().getName())
                .profile(guildRead.getGuildInfo().getProfile())
                .build();
    }
}
