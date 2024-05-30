package harmony.communityservice.guild.guild.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
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
@Sql("GuildCommandPersistenceAdapterTest.sql")
class GuildCommandPersistenceAdapterTest {

    @Autowired
    GuildCommandPersistenceAdapter guildCommandPersistenceAdapter;

    @Autowired
    GuildCommandRepository guildCommandRepository;


    @Test
    @DisplayName("길드 등록 테스트")
    void register_guild() {
        Guild guild = Guild.builder()
                .guildId(GuildId.make(Threshold.MIN.getValue()))
                .name("test")
                .profile("http://cdn.com/test")
                .managerId(UserId.make(1L))
                .inviteCode("8c475c80ffe4401385a040344046abce")
                .build();

        Long guildId = guildCommandPersistenceAdapter.register(guild);
        GuildEntity guildEntity = guildCommandRepository.findById(GuildIdJpaVO.make(guildId))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(guildEntity.getGuildInfo().getName(), guild.getProfileInfo().getName());
    }

    @Test
    @DisplayName("길드 가입 테스트")
    void join_guild() {
        String invitationCode = "first.1.1";

        Guild guild = guildCommandPersistenceAdapter.join(invitationCode, UserId.make(2L));

        assertEquals(guild.getInvitationCode(), invitationCode);
    }

    @Test
    @DisplayName("길드 삭제 테스트")
    void delete_guild() {
        guildCommandPersistenceAdapter.delete(GuildId.make(1L), UserId.make(1L));

        assertThrows(NotFoundDataException.class,
                () -> guildCommandRepository.findById(GuildIdJpaVO.make(1L)).orElseThrow(NotFoundDataException::new));
    }

}