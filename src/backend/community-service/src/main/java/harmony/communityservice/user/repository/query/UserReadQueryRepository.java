package harmony.communityservice.user.repository.query;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import java.util.List;
import java.util.Optional;

public interface UserReadQueryRepository {
    boolean existByUserIdAndGuildId(UserId userid, GuildId guildId);

    Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId);

    List<UserRead> findUserReadsByUserId(UserId userId);

    List<UserRead> findUserReadsByGuildId(GuildId guildId);
}
