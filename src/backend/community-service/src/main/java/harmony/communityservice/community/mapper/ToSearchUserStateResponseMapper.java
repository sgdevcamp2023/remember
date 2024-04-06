package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.CommonUserInfo;
import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.query.dto.SearchUserStateResponse;

public class ToSearchUserStateResponseMapper {

    public static SearchUserStateResponse convert(User user, String status) {
        CommonUserInfo commonUserInfo = user.getUserInfo().getCommonUserInfo();
        return SearchUserStateResponse.builder()
                .userId(user.getUserId())
                .profile(commonUserInfo.getProfile())
                .state(status)
                .userName(commonUserInfo.getNickname())
                .build();
    }

    public static SearchUserStateResponse convert(UserRead userRead) {
        return SearchUserStateResponse.builder()
                .userName(userRead.getUserInfo().getNickname())
                .profile(userRead.getUserInfo().getProfile())
                .userId(userRead.getUserId())
                .build();
    }
}
