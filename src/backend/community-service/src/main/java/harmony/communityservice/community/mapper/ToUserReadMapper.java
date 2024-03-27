package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.domain.UserRead;

public class ToUserReadMapper {

    public static UserRead convert(RegisterUserReadRequest registerUserReadRequest) {
        return UserRead.builder()
                .userId(registerUserReadRequest.userId())
                .guildId(registerUserReadRequest.guildId())
                .profile(registerUserReadRequest.profile())
                .nickname(registerUserReadRequest.nickname())
                .build();
    }
}
