package harmony.communityservice.user.repository.query.jpa;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.domain.UserReadId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserReadQueryRepository extends JpaRepository<UserRead, UserReadId> {
    boolean existsByGuildIdAndUserId(GuildId guildId, UserId userId);

    Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId);

    @Query("select ur from UserRead ur where ur.userId = :userId")
    List<UserRead> findUserReadByUserId(@Param("userId") UserId userId);

    @Query("select ur from UserRead ur where ur.guildId = :guildId")
    List<UserRead> findUserReadsByGuildId(@Param("guildId") GuildId guildId);
}
