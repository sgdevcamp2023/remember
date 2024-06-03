package harmony.communityservice.guild.guild.domain;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.guild.guild.domain.GuildUser.GuildUserId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GuildUserTest {


    @Test
    @DisplayName("식별자가 같으면 같은 객체로 인식")
    void same_guildUser() {
        GuildUser firstGuildUser = GuildUser.builder()
                .guildUserId(GuildUserId.make(1L))
                .userId(UserId.make(1L))
                .build();

        GuildUser secondGuildUser = GuildUser.builder()
                .guildUserId(GuildUserId.make(1L))
                .userId(UserId.make(2L))
                .build();

        boolean equals = firstGuildUser.equals(secondGuildUser);
        assertNotSame(firstGuildUser.getGuildUserId(),secondGuildUser.getGuildUserId());
        assertSame(equals, true);
    }

    @Test
    @DisplayName("식별자가 다르면 다른 객체로 인식")
    void different_guildUser() {
        GuildUser firstGuildUser = GuildUser.builder()
                .guildUserId(GuildUserId.make(1L))
                .userId(UserId.make(1L))
                .build();

        GuildUser secondGuildUser = GuildUser.builder()
                .guildUserId(GuildUserId.make(2L))
                .userId(UserId.make(2L))
                .build();

        boolean equals = firstGuildUser.equals(secondGuildUser);

        assertSame(equals, false);
    }

    @Test
    @DisplayName("UserId가 입력되지 않을 시 오류")
    void not_exists_user_id() {
        assertThrows(NotFoundDataException.class, () -> GuildUser.builder()
                .guildUserId(GuildUserId.make(1L))
                .build());
    }

    @ParameterizedTest
    @DisplayName("userId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L, -1000L})
    void user_id_range_threshold(long userId) {
        assertThrows(WrongThresholdRangeException.class, () -> GuildUser.builder()
                .guildUserId(GuildUserId.make(1L))
                .userId(UserId.make(userId))
                .build());
    }

    @ParameterizedTest
    @DisplayName("guildUserId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L, -1000L})
    void guild_user_id_range_threshold(long guildUserId) {
        assertThrows(WrongThresholdRangeException.class, () -> GuildUser.builder()
                .guildUserId(GuildUserId.make(guildUserId))
                .userId(UserId.make(1L))
                .build());
    }

}