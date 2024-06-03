package harmony.communityservice.guild.guild.adapter.out.persistence;

import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface GuildReadCommandRepository extends JpaRepository<GuildReadEntity, GuildReadIdJpaVO> {

    @Modifying
    @Query("delete from GuildReadEntity gr where gr.guildId = :guildId")
    void deleteGuildReadsByGuildId(@Param("guildId") GuildIdJpaVO guildId);


    @Modifying
    @Query("update GuildReadEntity gr "
            + "set gr.commonUserInfo.nickname = :nickname "
            + "where gr.guildId = :guildId and gr.userId = :userId")
    void updateNickname(@Param("nickname") String nickname, @Param("guildId") GuildIdJpaVO guildId,
                        @Param("userId") UserIdJpaVO userId);


    @Modifying
    @Query("update GuildReadEntity gr set gr.commonUserInfo.nickname = :nickname where gr.userId = :userId")
    void updateNicknames(@Param("nickname") String nickname, @Param("userId") UserIdJpaVO userId);

    Optional<GuildReadEntity> findByGuildIdAndUserId(GuildIdJpaVO guildId, UserIdJpaVO userId);
}
