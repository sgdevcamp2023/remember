package harmony.communityservice.guild.guild.repository.command.jpa;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaGuildCommandRepository extends JpaRepository<Guild, GuildId> {
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Guild> findByInviteCode(String code);

    @Modifying
    @Query(value = "delete from guild_user as gu where gu.guild_id = :guildId", nativeQuery = true)
    void deleteGuildUsersByGuildId(Long guildId);
}
