package harmony.communityservice.user.application.port.out;

import harmony.communityservice.user.domain.User.UserId;

public interface ModifyUserProfilePort {
    void modifyProfile(UserId userId, String profile);
}
