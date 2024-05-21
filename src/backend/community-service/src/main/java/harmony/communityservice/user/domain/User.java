package harmony.communityservice.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {

    private final UserId userId;

    private final UserInfo userInfo;

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        this.userId = UserId.make(userId);
        this.userInfo = UserInfo.make(email, profile, nickname);
    }


    public User modifiedNickname(String nickname) {
        UserInfo modifiedUserInfo = userInfo.modifyNickname(nickname);
        return new User(this.userId, modifiedUserInfo);
    }

    public User modifiedProfile(String profile) {
        UserInfo modifiedUserInfo = userInfo.modifyProfile(profile);
        return new User(this.userId, modifiedUserInfo);
    }

    @Getter
    @ToString
    public static class UserId {

        private final Long id;

        private UserId(Long id) {
            this.id = id;
        }

        public static UserId make(Long id) {
            return new UserId(id);
        }
    }
}
