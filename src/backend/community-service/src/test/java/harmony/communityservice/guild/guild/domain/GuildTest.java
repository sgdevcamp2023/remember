package harmony.communityservice.guild.guild.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuildTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_Guild() {

        Guild fisrtGuild = Guild.builder()
                .name("first_Guild")
                .profile("http://cdn.com/test")
                .inviteCode("test")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build();

        Guild secondGuild = Guild.builder()
                .name("second_Guild")
                .profile("http://cdn.com/test2")
                .inviteCode("test")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build();

        boolean equals = fisrtGuild.equals(secondGuild);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_Guild() {

        Guild fisrtGuild = Guild.builder()
                .name("first_Guild")
                .profile("http://cdn.com/test")
                .inviteCode("test")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build();

        Guild secondGuild = Guild.builder()
                .name("second_Guild")
                .profile("http://cdn.com/test2")
                .inviteCode("test")
                .guildId(GuildId.make(2L))
                .managerId(UserId.make(1L))
                .build();

        boolean equals = fisrtGuild.equals(secondGuild);

        assertSame(equals, false);
    }

    @Test
    @DisplayName("초대 코드가 없을 때 예외 처리 테스트")
    void not_exists_invitation_code() {
        assertThrows(NotFoundDataException.class, () -> Guild.builder()
                .name("first_Guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build());
    }

    @Test
    @DisplayName("길드명이 없을 때 예외 처리 테스트")
    void not_exists_name() {
        assertThrows(NotFoundDataException.class, () -> Guild.builder()
                .inviteCode("test")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build());
    }

    @Test
    @DisplayName("길드 프로필이 없을 때 예외 처리 테스트")
    void not_exists_profile() {
        assertThrows(NotFoundDataException.class, () -> Guild.builder()
                .inviteCode("test")
                .name("test_guild")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(1L))
                .build());
    }

    @Test
    @DisplayName("길드 매니저 아이디가 없을 때 예외 처리 테스트")
    void not_exists_manager() {
        assertThrows(NotFoundDataException.class, () -> Guild.builder()
                .inviteCode("test")
                .profile("http://cdn.com/test")
                .name("test_guild")
                .guildId(GuildId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("managerId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L})
    void manager_id_range_threshold(Long managerId) {
        assertThrows(WrongThresholdRangeException.class, () -> Guild.builder()
                .inviteCode("test")
                .profile("http://cdn.com/test")
                .name("test_guild")
                .guildId(GuildId.make(1L))
                .managerId(UserId.make(managerId))
                .build());
    }

    @ParameterizedTest
    @DisplayName("guildId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L})
    void guild_id_range_threshold(Long guildId) {
        assertThrows(WrongThresholdRangeException.class, () -> Guild.builder()
                .inviteCode("test")
                .profile("http://cdn.com/test")
                .name("test_guild")
                .guildId(GuildId.make(guildId))
                .managerId(UserId.make(1L))
                .build());
    }
}