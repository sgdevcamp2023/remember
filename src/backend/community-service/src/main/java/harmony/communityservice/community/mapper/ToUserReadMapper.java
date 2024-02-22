package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.UserReadRequestDto;
import harmony.communityservice.community.domain.UserRead;

public class ToUserReadMapper {

    public static UserRead convert(UserReadRequestDto requestDto) {
        return UserRead.builder()
                .userId(requestDto.getUserId())
                .guildId(requestDto.getGuildId())
                .profile(requestDto.getProfile())
                .nickname(requestDto.getNickname())
                .build();
    }
}
