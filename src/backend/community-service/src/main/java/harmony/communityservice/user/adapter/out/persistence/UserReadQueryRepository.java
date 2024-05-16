package harmony.communityservice.user.adapter.out.persistence;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;
import harmony.communityservice.user.adapter.out.persistence.UserReadId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserReadQueryRepository extends JpaRepository<UserReadEntity, UserReadId> {
    boolean existsByGuildIdAndUserId(GuildId guildId, UserId userId);

    Optional<UserReadEntity> findByUserIdAndGuildId(UserId userId, GuildId guildId);

    @Query("select ur from UserReadEntity ur where ur.userId = :userId")
    List<UserReadEntity> findUserReadByUserId(@Param("userId") UserId userId);

    @Query("select ur from UserReadEntity ur where ur.guildId = :guildId")
    List<UserReadEntity> findUserReadsByGuildId(@Param("guildId") GuildId guildId);
}
