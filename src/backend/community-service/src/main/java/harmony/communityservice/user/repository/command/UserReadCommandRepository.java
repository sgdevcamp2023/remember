package harmony.communityservice.user.repository.command;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import java.util.List;
import java.util.Optional;

public interface UserReadCommandRepository {

    void save(UserRead userRead);

    Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId);

    List<UserRead> findListByUserId(UserId userId);
}
