package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.domain.Guild;

public class ToSearchGuildReadRequestMapper {

    public static RegisterGuildReadRequest convert(Guild guild, Long userId) {
        return RegisterGuildReadRequest.builder()
                .userId(userId)
                .guildId(guild.getGuildId())
                .profile(guild.getProfile())
                .name(guild.getName())
                .build();
    }
}
