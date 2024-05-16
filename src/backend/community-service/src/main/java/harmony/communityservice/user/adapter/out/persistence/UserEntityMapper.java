package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.user.domain.User;

class UserEntityMapper {

    static UserJpaEntity convert(User user) {
        return UserJpaEntity.builder()
                .userId(UserIdJpaVO.make(user.getUserId().getId()))
                .email(user.getUserInfo().getEmail())
                .profile(user.getUserInfo().getCommonUserInfo().getProfile())
                .nickname(user.getUserInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
