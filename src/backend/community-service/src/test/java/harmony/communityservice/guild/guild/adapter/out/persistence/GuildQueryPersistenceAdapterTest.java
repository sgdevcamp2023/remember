package harmony.communityservice.guild.guild.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
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
@Sql("GuildQueryPersistenceAdapterTest.sql")
class GuildQueryPersistenceAdapterTest {

    @Autowired
    GuildQueryPersistenceAdapter guildQueryPersistenceAdapter;

    @Autowired
    GuildQueryRepository guildQueryRepository;

    @Test
    @DisplayName("길드 조회 테스트")
    void load_guild() {

        Guild guild = guildQueryPersistenceAdapter.loadById(GuildId.make(2L));

        GuildEntity guildEntity = guildQueryRepository.findById(GuildIdJpaVO.make(2L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(guild.getGuildId().getId(), guildEntity.getGuildId().getId());
        assertEquals(guild.getManagerId().getId(), guildEntity.getManagerId().getId());
    }

    @Test
    @DisplayName("길드 관리자인지 증명 테스트")

    void verify_manager() {
        guildQueryPersistenceAdapter.verify(GuildId.make(1L), UserId.make(1L));
        assertAll(()->assertThrows(NotFoundDataException.class,
                () -> guildQueryPersistenceAdapter.verify(GuildId.make(1L),
                        UserId.make(2L))),
                ()->assertThrows(NotFoundDataException.class,
                () -> guildQueryPersistenceAdapter.verify(GuildId.make(2L),
                        UserId.make(1L))),
                ()-> assertFalse(guildQueryRepository.existsByGuildIdAndManagerId(GuildIdJpaVO.make(2L),
                        UserIdJpaVO.make(1L)))
        );

    }

}