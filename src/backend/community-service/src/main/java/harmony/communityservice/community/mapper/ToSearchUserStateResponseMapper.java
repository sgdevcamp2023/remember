package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;

public class ToSearchUserStateResponseMapper {

    public static SearchUserStateResponse convert(User user, String status) {
        return SearchUserStateResponse.builder()
                .userId(user.getUserId())
                .profile(user.getProfile())
                .state(status)
                .userName(user.getNickname())
                .build();

    }
}
