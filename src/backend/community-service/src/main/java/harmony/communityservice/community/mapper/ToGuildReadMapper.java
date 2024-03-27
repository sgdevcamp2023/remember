package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.domain.GuildRead;

public class ToGuildReadMapper {

    public static GuildRead convert(RegisterGuildReadRequest registerGuildReadRequest) {
        return GuildRead.builder()
                .guildId(registerGuildReadRequest.guildId())
                .userId(registerGuildReadRequest.userId())
                .profile(registerGuildReadRequest.profile())
                .name(registerGuildReadRequest.name())
                .build();
    }
}
