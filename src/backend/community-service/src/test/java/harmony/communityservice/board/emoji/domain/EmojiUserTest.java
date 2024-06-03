package harmony.communityservice.board.emoji.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.user.domain.User.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmojiUserTest {

    @Test
    @DisplayName("식별자가 같으면 같은 객체로 인식")
    void same_guildUser() {
        EmojiUser firstEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));
        EmojiUser secondEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));

        boolean equals = firstEmojiUser.equals(secondEmojiUser);

        assertSame(equals, true);
        assertEquals(firstEmojiUser.getEmojiUserId(),secondEmojiUser.getEmojiUserId());
    }

    @Test
    @DisplayName("식별자가 다르면 다른 객체로 인식")
    void different_guildUser() {
        EmojiUser firstEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(1L));
        EmojiUser secondEmojiUser = new EmojiUser(UserId.make(1L), EmojiUserId.make(2L));

        boolean equals = firstEmojiUser.equals(secondEmojiUser);

        assertSame(equals, false);
    }

    @ParameterizedTest
    @DisplayName("userId의 범위 예외 처리 테스트")
    @ValueSource(longs = {0L, -1L, -1000L, -10000L})
    void user_id_range_threshold(Long userId) {
        assertThrows(WrongThresholdRangeException.class, () -> new EmojiUser(UserId.make(userId), EmojiUserId.make(1L)));

    }

    @ParameterizedTest
    @DisplayName("emojiUserId의 범위 예외 처리 테스트")
    @ValueSource(longs = {0L, -1L, -1000L, -10000L})
    void emoji_user_id_range_threshold(Long emojiUserId) {
        assertThrows(WrongThresholdRangeException.class, () -> new EmojiUser(UserId.make(1L), EmojiUserId.make(emojiUserId)));

    }
}