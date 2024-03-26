package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.domain.Guild;
import java.util.UUID;

public class ToGuildMapper {

    public static Guild convert(RegisterGuildRequest requestDto, String profile) {
        return Guild.builder()
                .name(requestDto.getName())
                .profile(profile)
                .managerId(requestDto.getManagerId())
                .inviteCode(UUID.randomUUID().toString().replace("-", ""))
                .build();
    }
}
