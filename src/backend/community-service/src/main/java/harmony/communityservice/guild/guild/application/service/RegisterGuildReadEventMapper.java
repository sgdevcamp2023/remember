package harmony.communityservice.guild.guild.application.service;

import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.guild.guild.domain.Guild;

public class RegisterGuildReadEventMapper {

    public static RegisterGuildReadEvent convert(Guild guild, Long userId) {
        return RegisterGuildReadEvent.builder()
                .userId(userId)
                .guildId(guild.getGuildId().getId())
                .profile(guild.getProfileInfo().getProfile())
                .name(guild.getProfileInfo().getName())
                .build();
    }
}
