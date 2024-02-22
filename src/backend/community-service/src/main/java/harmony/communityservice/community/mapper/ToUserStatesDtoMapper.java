package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.query.dto.UserStateResponseDto;

public class ToUserStatesDtoMapper {

    public static UserStateResponseDto convert(User user, String status) {
        return UserStateResponseDto.builder()
                .userId(user.getUserId())
                .profile(user.getProfile())
                .state(status)
                .userName(user.getNickname())
                .build();

    }
}
