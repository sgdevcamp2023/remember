package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.domain.Guild;

public class ToGuildReadRequestDtoMapper {

    public static GuildReadRequestDto convert(Guild guild, Long userId) {
        return GuildReadRequestDto.builder()
                .userId(userId)
                .guildId(guild.getGuildId())
                .profile(guild.getProfile())
                .name(guild.getName())
                .build();
    }
}
