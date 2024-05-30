package harmony.communityservice.guild.guild.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;
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
@Sql("GuildReadCommandPersistenceAdapterTest.sql")
class GuildReadCommandPersistenceAdapterTest {

    @Autowired
    GuildReadCommandPersistenceAdapter guildReadCommandPersistenceAdapter;

    @Autowired
    GuildReadCommandRepository guildReadCommandRepository;

    @Test
    @DisplayName("길드_읽기 저장 테스트")
    void register_guild_read() {
        GuildRead guildRead = GuildRead.builder()
                .userProfile("https://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1000L))
                .guildReadId(GuildReadId.make(Threshold.MIN.getValue()))
                .profile("https://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1000L))
                .build();

        guildReadCommandPersistenceAdapter.register(guildRead);

        GuildReadEntity guildReadEntity = guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(1000L),
                        UserIdJpaVO.make(1000L)).orElseThrow(NotFoundDataException::new);

        assertEquals(guildReadEntity.getGuildId().getId(), 1000L);
        assertEquals(guildReadEntity.getUserId().getId(), 1000L);
        assertEquals(guildReadEntity.getGuildInfo().getName(), "first_guild");
    }

    @Test
    @DisplayName("guild_read 삭제 테스트")
    void delete_guild_reads() {
        guildReadCommandPersistenceAdapter.delete(GuildId.make(1L));

        assertThrows(NotFoundDataException.class,
                () -> guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(1L), UserIdJpaVO.make(1L))
                        .orElseThrow(NotFoundDataException::new));
    }

    @Test
    @DisplayName("guild_read nickname 수정 테스트")
    void modify_guild_read() {
        guildReadCommandPersistenceAdapter.modifyNickname(GuildId.make(1L), UserId.make(1L),"NEW_NICKNAME");

        GuildReadEntity guildReadEntity = guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(1L),
                        UserIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(guildReadEntity.getCommonUserInfo().getNickname(),"NEW_NICKNAME");
    }

    @Test
    @DisplayName("guild_reads nickname 수정 테스트")
    void modify_guild_reads() {
        guildReadCommandPersistenceAdapter.modify(UserId.make(1L),"NEW_NICKNAME");

        GuildReadEntity firstGuildRead = guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(1L),
                UserIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new);
        GuildReadEntity secondGuildRead = guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(2L),
                UserIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new);

        assertEquals(firstGuildRead.getCommonUserInfo().getNickname(),"NEW_NICKNAME");
        assertEquals(firstGuildRead.getCommonUserInfo().getNickname(),secondGuildRead.getCommonUserInfo().getNickname());
    }

    @Test
    @DisplayName("guild_read 조회 테스트")
    void load_guild_read() {
        GuildRead guildRead = guildReadCommandPersistenceAdapter.loadByUserIdAndGuildId(UserId.make(1L),
                GuildId.make(1L));

        GuildReadEntity guildReadEntity = guildReadCommandRepository.findByGuildIdAndUserId(GuildIdJpaVO.make(1L),
                UserIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new);

        assertEquals(guildRead.getGuildReadId().getId(),guildReadEntity.getGuildReadId().getId());
        assertEquals(guildRead.getProfileInfo().getName(),guildReadEntity.getGuildInfo().getName());
    }
}