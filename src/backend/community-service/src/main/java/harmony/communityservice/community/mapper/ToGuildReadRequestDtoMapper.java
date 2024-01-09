package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.domain.Guild;

public class ToGuildReadRequestDtoMapper {

    public static GuildReadRequestDto convert(Guild guild) {
        return GuildReadRequestDto.builder()
                .userId(guild.getManagerId())
                .guildId(guild.getGuildId())
                .profile(guild.getProfile())
                .name(guild.getName())
                .build();
    }
}
