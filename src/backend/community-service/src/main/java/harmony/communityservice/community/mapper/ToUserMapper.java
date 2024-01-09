package harmony.communityservice.community.mapper;

import harmony.communityservice.community.command.domain.User;
import harmony.communityservice.community.command.dto.UserStoreRequestDto;

public class ToUserMapper {

    public static User convert(UserStoreRequestDto requestDto) {
        return User.builder()
                .userId(requestDto.getUserId())
                .nickname(requestDto.getName())
                .profile(requestDto.getProfile())
                .email(requestDto.getEmail())
                .build();
    }
}
