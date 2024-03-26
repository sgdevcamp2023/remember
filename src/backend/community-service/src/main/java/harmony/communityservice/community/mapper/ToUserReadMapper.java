package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.domain.UserRead;

public class ToUserReadMapper {

    public static UserRead convert(RegisterUserReadRequest requestDto) {
        return UserRead.builder()
                .userId(requestDto.getUserId())
                .guildId(requestDto.getGuildId())
                .profile(requestDto.getProfile())
                .nickname(requestDto.getNickname())
                .build();
    }
}
