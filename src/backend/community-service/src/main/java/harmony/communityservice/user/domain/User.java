package harmony.communityservice.user.domain;

import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.user.domain.User.UserId;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User extends Domain<User, UserId> {

    private final UserId userId;

    private final UserInfo userInfo;

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        verifyUserId(userId);
        this.userId = UserId.make(userId);
        this.userInfo = UserInfo.make(email, profile, nickname);
    }

    private void verifyUserId(Long userId) {
        if (userId != null && userId < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("userId가 1 미만입니다");
        }
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
            return new Object[]{id};
        }
    }
}
