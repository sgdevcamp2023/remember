package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;

public class ToSearchUserStateResponseMapper {

    public static SearchUserStateResponse convert(User user, String status) {
        return SearchUserStateResponse.builder()
                .userId(user.getUserId())
                .profile(user.getUserInfo().getProfile())
                .state(status)
                .userName(user.getUserInfo().getNickname())
                .build();
    }

    public static SearchUserStateResponse convert(UserRead userRead) {
        return SearchUserStateResponse.builder()
                .userName(userRead.getNickname())
                .profile(userRead.getProfile())
                .userId(userRead.getUserId())
                .build();
    }
}
