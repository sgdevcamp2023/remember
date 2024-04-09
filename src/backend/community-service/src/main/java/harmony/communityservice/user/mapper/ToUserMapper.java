package harmony.communityservice.user.mapper;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.dto.RegisterUserRequest;

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
