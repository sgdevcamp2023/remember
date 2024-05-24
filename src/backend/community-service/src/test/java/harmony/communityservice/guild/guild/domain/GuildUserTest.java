package harmony.communityservice.guild.guild.domain;

import static org.junit.jupiter.api.Assertions.assertSame;

import harmony.communityservice.guild.guild.domain.GuildUser.GuildUserId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}