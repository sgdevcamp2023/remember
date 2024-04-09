package harmony.communityservice.user.mapper;

import harmony.communityservice.user.dto.RegisterUserReadRequest;
import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserRead;

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
