package harmony.communityservice.guild.guild.dto;

import harmony.communityservice.guild.guild.domain.Guild;

public class ToSearchGuildReadRequestMapper {

    public static RegisterGuildReadRequest convert(Guild guild, Long userId) {
        return RegisterGuildReadRequest.builder()
                .userId(userId)
                .guildId(guild.getGuildId())
                .profile(guild.getGuildInfo().getProfile())
                .name(guild.getGuildInfo().getName())
                .build();
    }
}
