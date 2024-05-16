package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.UUID;

public class ToGuildMapper {

    public static Guild convert(RegisterGuildRequest registerGuildRequest, String profile) {
        return Guild.builder()
                .name(registerGuildRequest.name())
                .profile(profile)
                .managerId(UserId.make(registerGuildRequest.managerId()))
                .inviteCode(UUID.randomUUID().toString().replace("-", ""))
                .build();
    }
}
