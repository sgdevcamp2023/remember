package harmony.communityservice.board.emoji.domain;

import static org.junit.jupiter.api.Assertions.assertSame;

import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmojiUserTest {

    @Test
    @DisplayName("식별자가 같으면 같은 객체로 인식")
    void same_guildUser() {
        EmojiUser firstEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));
        EmojiUser secondEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));

        boolean equals = firstEmojiUser.equals(secondEmojiUser);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("식별자가 다르면 다른 객체로 인식")
    void different_guildUser() {
        EmojiUser firstEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));
        EmojiUser secondEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(2L));

        boolean equals = firstEmojiUser.equals(secondEmojiUser);

        assertSame(equals, false);
    }
}