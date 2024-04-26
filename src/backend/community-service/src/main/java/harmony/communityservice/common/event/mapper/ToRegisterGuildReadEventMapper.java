package harmony.communityservice.common.event.mapper;

import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.guild.guild.domain.Guild;

public class ToRegisterGuildReadEventMapper {

    public static RegisterGuildReadEvent convert(Guild guild, Long userId) {
        return RegisterGuildReadEvent.builder()
                .userId(userId)
                .guildId(guild.getGuildId().getId())
                .profile(guild.getGuildInfo().getProfile())
                .name(guild.getGuildInfo().getName())
                .build();
    }
}
