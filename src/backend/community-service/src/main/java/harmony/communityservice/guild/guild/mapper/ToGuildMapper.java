package harmony.communityservice.guild.guild.mapper;

import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.domain.Guild;
import java.util.UUID;

public class ToGuildMapper {

    public static Guild convert(RegisterGuildRequest registerGuildRequest, String profile, Long userId) {
        return Guild.builder()
                .name(registerGuildRequest.name())
                .profile(profile)
                .managerId(registerGuildRequest.managerId())
                .inviteCode(UUID.randomUUID().toString().replace("-", ""))
                .userId(userId)
                .build();
    }
}
