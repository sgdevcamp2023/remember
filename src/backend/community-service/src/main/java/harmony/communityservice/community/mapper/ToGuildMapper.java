package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.domain.Guild;
import java.util.UUID;

public class ToGuildMapper {

    public static Guild convert(RegisterGuildRequest registerGuildRequest, String profile) {
        return Guild.builder()
                .name(registerGuildRequest.name())
                .profile(profile)
                .managerId(registerGuildRequest.managerId())
                .inviteCode(UUID.randomUUID().toString().replace("-", ""))
                .build();
    }
}
