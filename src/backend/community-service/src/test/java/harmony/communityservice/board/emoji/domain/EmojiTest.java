package harmony.communityservice.board.emoji.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.user.domain.User.UserId;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmojiTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_emoji() {
        List<EmojiUser> emojiUsers = new ArrayList<>(
                List.of(new EmojiUser(UserId.make(1L), EmojiUserId.make(1L))));

        Emoji firstEmoji = Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .boardId(BoardId.make(1L))
                .build();

        Emoji secondEmoji = Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .boardId(BoardId.make(1L))
                .build();

        boolean equals = firstEmoji.equals(secondEmoji);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_category() {
        List<EmojiUser> emojiUsers = new ArrayList<>(
                List.of(new EmojiUser(UserId.make(1L), EmojiUserId.make(1L))));

        Emoji firstEmoji = Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .boardId(BoardId.make(1L))
                .emojiUsers(emojiUsers)
                .build();

        Emoji secondEmoji = Emoji.builder()
                .emojiId(EmojiId.make(2L))
                .emojiType(1L)
                .boardId(BoardId.make(1L))
                .emojiUsers(emojiUsers)
                .build();

        boolean equals = firstEmoji.equals(secondEmoji);

        assertSame(false, equals);
    }

    @Test
    @DisplayName("boardId가 없으면 예외 처리 테스트")
    void not_exists_boardId() {
        List<EmojiUser> emojiUsers = new ArrayList<>(
                List.of(new EmojiUser(UserId.make(1L), EmojiUserId.make(1L))));

        assertThrows(NotFoundDataException.class, () -> Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .build());
    }

    @Test
    @DisplayName("emojiType이 없으면 예외 처리 테스트")
    void not_exists_emoji_type() {
        List<EmojiUser> emojiUsers = new ArrayList<>(
                List.of(new EmojiUser(UserId.make(1L), EmojiUserId.make(1L))));

        assertThrows(NotFoundDataException.class, () -> Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .boardId(BoardId.make(1L))
                .emojiUsers(emojiUsers)
                .build());
    }

    @Test
    @DisplayName("emojiUsers가 없으면 예외 처리 테스트")
    void not_exists_emoji_users() {
        assertThrows(NotFoundDataException.class, () -> Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .boardId(BoardId.make(1L))
                .emojiType(1L)
                .build());
    }

    @Test
    @DisplayName("emojiUsers에 emojiUser가 없으면 예외 처리 테스트")
    void emoji_users_size_zero() {
        List<EmojiUser> emojiUsers = new ArrayList<>();

        assertThrows(NotFoundDataException.class, () -> Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .boardId(BoardId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .build());
    }

    @ParameterizedTest
    @DisplayName("emojiType 범위 넘을 때 예외 처리 테스트")
    @ValueSource(longs = {0L,257L,258L,1000L, -1L, -1110L})
    void emoji_type_range_check_threshold(Long emojiType) {
        List<EmojiUser> emojiUsers = new ArrayList<>(
                List.of(new EmojiUser(UserId.make(1L), EmojiUserId.make(1L))));

        assertThrows(WrongThresholdRangeException.class,()->Emoji.builder()
                .emojiId(EmojiId.make(1L))
                .emojiType(emojiType)
                .boardId(BoardId.make(1L))
                .emojiUsers(emojiUsers)
                .build());

    }
}