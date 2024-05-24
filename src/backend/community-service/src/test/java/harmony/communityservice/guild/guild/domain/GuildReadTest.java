package harmony.communityservice.guild.guild.domain;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuildReadTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_Guild() {

        GuildRead firstGuildRead = GuildRead.builder()
                .name("first_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build();

        GuildRead secondGuildRead = GuildRead.builder()
                .name("second_guild")
                .profile("http://cdn.com/test2")
                .guildId(GuildId.make(2L))
                .userId(UserId.make(2L))
                .userNickname("test2")
                .userProfile("http://cdn.com/user_test_profile2")
                .guildReadId(GuildReadId.make(1L))
                .build();

        boolean equals = firstGuildRead.equals(secondGuildRead);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_guild() {

        GuildRead firstGuildRead = GuildRead.builder()
                .name("first_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build();

        GuildRead secondGuildRead = GuildRead.builder()
                .name("second_guild")
                .profile("http://cdn.com/test2")
                .guildId(GuildId.make(2L))
                .userId(UserId.make(2L))
                .userNickname("test2")
                .userProfile("http://cdn.com/user_test_profile2")
                .guildReadId(GuildReadId.make(2L))
                .build();

        boolean equals = firstGuildRead.equals(secondGuildRead);

        assertSame(equals, false);
    }

    @Test
    @DisplayName("Guild name이 없으면 예외 처리 인식 테스트")
    void not_exists_guild_name() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @Test
    @DisplayName("Guild profile이 없으면 예외 처리 인식 테스트")
    void not_exists_guild_profile() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .name("test_guild")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @Test
    @DisplayName("guild Id가 없으면 예외 처리 인식 테스트")
    void not_exists_guild_id() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .name("test_guild")
                .profile("http://cdn.com/test")
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @Test
    @DisplayName("user Id가 없으면 예외 처리 인식 테스트")
    void not_exists_user_id() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .name("test_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @Test
    @DisplayName("user nickname이 없으면 예외 처리 인식 테스트")
    void not_exists_user_nickname() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .name("test_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @Test
    @DisplayName("user Profile이 없으면 예외 처리 인식 테스트")
    void not_exists_user_profile() {
        assertThrows(NotFoundDataException.class, () -> GuildRead.builder()
                .name("test_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("guildId 범위 테스트")
    @ValueSource(longs = {0L,-1L,-10L,-100L,-1000L})
    void guild_id_range_threshold(long guildId) {

        assertThrows(WrongThresholdRangeException.class,()->GuildRead.builder()
                .name("first_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(guildId))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("userId 범위 테스트")
    @ValueSource(longs = {0L,-1L,-10L,-100L,-1000L})
    void user_id_range_threshold(long userId) {

        assertThrows(WrongThresholdRangeException.class,()->GuildRead.builder()
                .name("first_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(userId))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("guildReadId 범위 테스트")
    @ValueSource(longs = {0L,-1L,-10L,-100L,-1000L})
    void guild_read_id_range_threshold(long guildReadId) {
        assertThrows(WrongThresholdRangeException.class,()->GuildRead.builder()
                .name("first_guild")
                .profile("http://cdn.com/test")
                .guildId(GuildId.make(1L))
                .userId(UserId.make(1L))
                .userNickname("test1")
                .userProfile("http://cdn.com/user_test_profile")
                .guildReadId(GuildReadId.make(guildReadId))
                .build());
    }
}