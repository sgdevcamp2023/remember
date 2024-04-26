package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.domain.UserId;

public class ToGuildReadMapper {

    public static GuildRead convert(RegisterGuildReadRequest registerGuildReadRequest) {
        return GuildRead.builder()
                .guildId(GuildId.make(registerGuildReadRequest.guildId()))
                .userId(UserId.make(registerGuildReadRequest.userId()))
                .profile(registerGuildReadRequest.profile())
                .name(registerGuildReadRequest.name())
                .build();
    }
}
