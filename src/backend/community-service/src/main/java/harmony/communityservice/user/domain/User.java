package harmony.communityservice.user.domain;

import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends Domain<User, UserId> {

    private final UserId userId;

    private final UserInfo userInfo;

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        this.userId = UserId.make(userId);
        this.userInfo = UserInfo.make(email, profile, nickname);
    }

    public User modifiedProfile(String profile) {
        UserInfo modifiedUserInfo = userInfo.modifyProfile(profile);
        return new User(this.userId, modifiedUserInfo);
    }

    @Override
    public UserId getId() {
        return userId;
    }

    @Getter
    public static class UserId extends ValueObject<UserId> {

        private final Long id;

        private UserId(Long id) {
            this.id = id;
        }

        public static UserId make(Long id) {
            return new UserId(id);
        }

        @Override
        protected Object[] getEqualityFields() {
            return  new Object[] { id };
        }
    }
}
