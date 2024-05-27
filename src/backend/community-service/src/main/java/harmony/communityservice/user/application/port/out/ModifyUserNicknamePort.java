package harmony.communityservice.user.application.port.out;

import harmony.communityservice.user.domain.User.UserId;

public interface ModifyUserNicknamePort {
    void modifyNickname(UserId userId, String nickname);
}
