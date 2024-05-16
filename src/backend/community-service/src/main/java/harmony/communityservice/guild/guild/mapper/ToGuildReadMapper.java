package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;

public class ToGuildReadMapper {

    public static GuildRead convert(RegisterGuildReadRequest registerGuildReadRequest) {
        return GuildRead.builder()
                .guildId(GuildId.make(registerGuildReadRequest.guildId()))
                .userId(UserIdJpaVO.make(registerGuildReadRequest.userId()))
                .profile(registerGuildReadRequest.profile())
                .name(registerGuildReadRequest.name())
                .build();
    }
}
