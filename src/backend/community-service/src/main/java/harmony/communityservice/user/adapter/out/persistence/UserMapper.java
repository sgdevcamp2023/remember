package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.user.domain.User;

class UserMapper {

    static User convert(UserJpaEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId().getId())
                .email(userEntity.getUserInfo().getEmail())
                .profile(userEntity.getUserInfo().getCommonUserInfo().getProfile())
                .nickname(userEntity.getUserInfo().getCommonUserInfo().getNickname())
                .build();
    }
}
