package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.domain.GuildRead;

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
