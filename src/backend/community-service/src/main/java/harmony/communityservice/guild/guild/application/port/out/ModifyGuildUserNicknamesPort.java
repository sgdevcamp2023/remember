package harmony.communityservice.guild.guild.application.port.out;

import harmony.communityservice.user.domain.User.UserId;

public interface ModifyGuildUserNicknamesPort {
    void modify(UserId userId, String nickname);
}
