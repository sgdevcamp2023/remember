package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.command.dto.RegisterUserRequest;

public class ToUserMapper {

    public static User convert(RegisterUserRequest registerUserRequest) {
        return User.builder()
                .userId(registerUserRequest.userId())
                .nickname(registerUserRequest.name())
                .profile(registerUserRequest.profile())
                .email(registerUserRequest.email())
                .build();
    }
}
