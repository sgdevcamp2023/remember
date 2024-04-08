package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.dto.RegisterUserReadRequest;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;

public class ToUserReadMapper {

    public static UserRead convert(RegisterUserReadRequest registerUserReadRequest, User user) {
        return UserRead.builder()
                .userId(registerUserReadRequest.userId())
                .guildId(registerUserReadRequest.guildId())
                .profile(user.getUserInfo().getCommonUserInfo().getProfile())
                .nickname(user.getUserInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
