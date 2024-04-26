package harmony.communityservice.user.repository.command.jpa;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.domain.UserReadId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserReadCommandRepository extends JpaRepository<UserRead, UserReadId> {

    Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId);

    List<UserRead> findUserReadsByUserId(UserId userId);
}
