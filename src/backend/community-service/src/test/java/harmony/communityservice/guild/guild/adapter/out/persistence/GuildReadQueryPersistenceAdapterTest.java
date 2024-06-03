package harmony.communityservice.guild.guild.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("GuildReadQueryPersistenceAdapterTest.sql")
class GuildReadQueryPersistenceAdapterTest {


    @Autowired
    GuildReadQueryPersistenceAdapter guildReadQueryPersistenceAdapter;

    @Autowired
    GuildReadQueryRepository guildReadQueryRepository;

    @Test
    @DisplayName("guildId를 통해 guild_read 조회 테스트")
    void load_guild_reads() {
        List<GuildRead> guildReads = guildReadQueryPersistenceAdapter.loadListByGuildId(GuildId.make(1L));
        List<GuildReadEntity> guildReadEntities = guildReadQueryRepository.findByGuildId(GuildIdJpaVO.make(1L));

        assertEquals(guildReads.size(),guildReadEntities.size());
        assertEquals(guildReads.size(),3L);
    }

    @Test
    @DisplayName("userId를 통해 guild_read 조회 테스트")
    void load_guild_reads_userId() {
        List<GuildRead> guildReads = guildReadQueryPersistenceAdapter.loadListByUserId(UserId.make(1L));
        List<GuildReadEntity> guildReadEntities = guildReadQueryRepository.findGuildReadsByUserId(UserIdJpaVO.make(1L));

        assertEquals(guildReads.size(),guildReadEntities.size());
        assertEquals(guildReads.size(),2L);
    }

    @Test
    @DisplayName("길드에 속한 유저인지 인증 테스트")
    void verify_user_guild() {
        guildReadQueryPersistenceAdapter.verify(UserId.make(1L),GuildId.make(1L));
        assertAll(()->assertThrows(NotFoundDataException.class,
                        () -> guildReadQueryPersistenceAdapter.verify(UserId.make(2L),
                                GuildId.make(2L))),
                ()->assertThrows(NotFoundDataException.class,
                        () -> guildReadQueryPersistenceAdapter.verify(UserId.make(4L),
                                GuildId.make(1L))),
                ()-> assertFalse(guildReadQueryRepository.existsByGuildIdAndUserId(GuildIdJpaVO.make(2L),
                        UserIdJpaVO.make(2L)))
        );
    }

    @Test
    @DisplayName("유저가 속한 길드의 ID 조회 테스트")
    void load_guild_ids() {
        List<GuildId> guildIds = guildReadQueryPersistenceAdapter.loadList(UserId.make(1L));
        List<GuildIdJpaVO> guildIdJpaVOS = guildReadQueryRepository.findGuildIdsByUserId(UserIdJpaVO.make(1L));

        assertEquals(guildIdJpaVOS.size(),guildIds.size());
        assertEquals(2L, guildIds.size());
    }
}