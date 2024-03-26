package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.command.dto.RegisterUserRequest;

public class ToUserMapper {

    public static User convert(RegisterUserRequest requestDto) {
        return User.builder()
                .userId(requestDto.getUserId())
                .nickname(requestDto.getName())
                .profile(requestDto.getProfile())
                .email(requestDto.getEmail())
                .build();
    }
}
