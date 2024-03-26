package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterGuildReadRequest;
import harmony.communityservice.community.domain.GuildRead;

public class ToGuildReadMapper {

    public static GuildRead convert(RegisterGuildReadRequest requestDto) {
        return GuildRead.builder()
                .guildId(requestDto.getGuildId())
                .userId(requestDto.getUserId())
                .profile(requestDto.getProfile())
                .name(requestDto.getName())
                .build();
    }
}
