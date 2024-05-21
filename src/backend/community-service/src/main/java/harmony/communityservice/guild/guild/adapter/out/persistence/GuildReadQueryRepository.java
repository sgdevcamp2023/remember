package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface GuildReadQueryRepository extends JpaRepository<GuildReadEntity, GuildReadIdJpaVO> {

    @Query("select gr from GuildReadEntity gr where gr.userId = :userId")
    List<GuildReadEntity> findGuildReadsByUserId(UserIdJpaVO userId);

    @Query("select gr.guildId from GuildReadEntity gr where gr.userId = :userId")
    List<GuildIdJpaVO> findGuildIdsByUserId(UserIdJpaVO userId);

    @Query("select gr from GuildReadEntity gr where gr.guildId = :guildId")
    List<GuildReadEntity> findByGuildId(@Param("guildId") GuildIdJpaVO guildId);

    boolean existsByGuildIdAndUserId(GuildIdJpaVO guildId, UserIdJpaVO userId);
}
