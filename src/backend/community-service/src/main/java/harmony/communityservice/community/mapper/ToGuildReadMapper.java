package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.GuildReadRequestDto;
import harmony.communityservice.community.domain.GuildRead;

public class ToGuildReadMapper {

    public static GuildRead convert(GuildReadRequestDto requestDto) {
        return GuildRead.builder()
                .guildId(requestDto.getGuildId())
                .userId(requestDto.getUserId())
                .profile(requestDto.getProfile())
                .name(requestDto.getName())
                .build();
    }
}
