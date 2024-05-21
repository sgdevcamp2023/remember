package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface GuildCommandRepository extends JpaRepository<GuildEntity, GuildIdJpaVO> {
    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<GuildEntity> findByInviteCode(String code);

    @Modifying
    @Query(value = "delete from guild_user as gu where gu.guild_id = :guildId", nativeQuery = true)
    void deleteGuildUsersByGuildId(Long guildId);

    @Modifying
    @Query(value = "delete from GuildEntity g where g.guildId = :guildId and g.managerId = :userId")
    void deleteGuildByGuildIdAndManagerId(@Param("guildId") GuildIdJpaVO guildId, @Param("userId") UserIdJpaVO userId);
}
