package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface GuildQueryRepository extends JpaRepository<GuildEntity, GuildIdJpaVO> {
    Optional<GuildEntity> findByInviteCode(String code);

    boolean existsByGuildIdAndManagerId(GuildIdJpaVO guildId, UserIdJpaVO managerId);
}
