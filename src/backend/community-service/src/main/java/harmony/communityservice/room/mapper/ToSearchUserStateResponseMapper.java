package harmony.communityservice.room.mapper;

import harmony.communityservice.generic.CommonUserInfoJpaVO;
import harmony.communityservice.room.dto.SearchUserStateResponse;
import harmony.communityservice.user.adapter.out.persistence.UserJpaEntity;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;

public class ToSearchUserStateResponseMapper {

    public static SearchUserStateResponse convert(UserJpaEntity user, String status) {
        CommonUserInfoJpaVO commonUserInfo = user.getUserInfo().getCommonUserInfo();
        return SearchUserStateResponse.builder()
                .userId(user.getUserId().getId())
                .profile(commonUserInfo.getProfile())
                .state(status)
                .userName(commonUserInfo.getNickname())
                .build();
    }

    public static SearchUserStateResponse convert(UserReadEntity userRead) {
        return SearchUserStateResponse.builder()
                .userName(userRead.getUserInfo().getNickname())
                .profile(userRead.getUserInfo().getProfile())
                .userId(userRead.getUserId().getId())
                .build();
    }
}
