package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;

public class ToRegisterGuildReadRequestMapper {

    public static RegisterGuildReadRequest convert(RegisterGuildReadEvent event) {
        return RegisterGuildReadRequest.builder()
                .userId(event.userId())
                .guildId(event.guildId())
                .profile(event.profile())
                .name(event.name())
                .build();
    }
}
