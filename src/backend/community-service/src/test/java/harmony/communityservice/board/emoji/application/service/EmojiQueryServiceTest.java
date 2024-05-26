package harmony.communityservice.board.emoji.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojiResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.LoadEmojisPort;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.board.emoji.domain.EmojiUser;
import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmojiQueryServiceTest {

    @Mock
    LoadEmojiPort loadEmojiPort;
    @Mock
    LoadEmojisPort loadEmojisPort;
    EmojiQueryService emojiQueryService;

    @BeforeEach
    void setting() {
        emojiQueryService = new EmojiQueryService(loadEmojiPort, loadEmojisPort);
    }

    @Test
    @DisplayName("이모지 조회 테스트")
    void load_emoji() {
        assertNotNull(emojiQueryService);

        EmojiUser first = new EmojiUser(UserId.make(1L),
                EmojiUserId.make(1L));
        EmojiUser second = new EmojiUser(UserId.make(2L),
                EmojiUserId.make(2L));
        EmojiUser third = new EmojiUser(UserId.make(3L),
                EmojiUserId.make(3L));
        List<EmojiUser> emojiUsers = List.of(first, second, third);
        Emoji emoji = Emoji.builder()
                .boardId((BoardId.make(1L)))
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .build();

        given(loadEmojiPort.loadByBoardIdAndEmojiType(BoardId.make(1L), 1L)).willReturn(emoji);

        Emoji testEmoji = emojiQueryService.load(BoardId.make(1L), 1L);

        assertEquals(emoji, testEmoji);
        then(loadEmojiPort).should(times(1)).loadByBoardIdAndEmojiType(BoardId.make(1L), 1L);
    }

    @Test
    @DisplayName("emojiId를 통해 이모지 조회 테스트")
    void load_emoji_emojiId() {
        assertNotNull(emojiQueryService);

        EmojiUser first = new EmojiUser(UserId.make(1L),
                EmojiUserId.make(1L));
        EmojiUser second = new EmojiUser(UserId.make(2L),
                EmojiUserId.make(2L));
        EmojiUser third = new EmojiUser(UserId.make(3L),
                EmojiUserId.make(3L));
        List<EmojiUser> emojiUsers = List.of(first, second, third);
        Emoji emoji = Emoji.builder()
                .boardId((BoardId.make(1L)))
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(emojiUsers)
                .build();
        given(loadEmojiPort.load(EmojiId.make(1L))).willReturn(emoji);

        Emoji testEmoji = emojiQueryService.loadById(EmojiId.make(1L));

        assertEquals(emoji, testEmoji);
        then(loadEmojiPort).should(times(1)).load(EmojiId.make(1L));
    }

    @Test
    @DisplayName("게시글 안의 이모지 조회 리스트")
    void load_board_emojis() {
        assertNotNull(emojiQueryService);

        EmojiUser firstEmojiUser = new EmojiUser(UserId.make(1L),
                EmojiUserId.make(1L));
        EmojiUser secondEmojiUser = new EmojiUser(UserId.make(2L),
                EmojiUserId.make(2L));
        EmojiUser thirdEmojiUser = new EmojiUser(UserId.make(3L),
                EmojiUserId.make(3L));
        List<EmojiUser> first = List.of(firstEmojiUser, secondEmojiUser, thirdEmojiUser);
        Emoji firstEmoji = Emoji.builder()
                .boardId((BoardId.make(1L)))
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(first)
                .build();
        EmojiUser fourthEmojiUser = new EmojiUser(UserId.make(1L),
                EmojiUserId.make(1L));
        EmojiUser fifthEmojiUser = new EmojiUser(UserId.make(2L),
                EmojiUserId.make(2L));
        EmojiUser sixthEmojiUser = new EmojiUser(UserId.make(3L),
                EmojiUserId.make(3L));
        List<EmojiUser> second = List.of(fourthEmojiUser, fifthEmojiUser, sixthEmojiUser);
        Emoji secondEmoji = Emoji.builder()
                .boardId((BoardId.make(1L)))
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(second)
                .build();
        EmojiUser seventhEmojiUser = new EmojiUser(UserId.make(1L),
                EmojiUserId.make(1L));
        EmojiUser eighthEmojiUser = new EmojiUser(UserId.make(2L),
                EmojiUserId.make(2L));
        EmojiUser ninethEmojiUser = new EmojiUser(UserId.make(3L),
                EmojiUserId.make(3L));
        List<EmojiUser> third = List.of(seventhEmojiUser, eighthEmojiUser, ninethEmojiUser);
        Emoji thirdEmoji = Emoji.builder()
                .boardId((BoardId.make(1L)))
                .emojiId(EmojiId.make(1L))
                .emojiType(1L)
                .emojiUsers(third)
                .build();
        List<Long> firstTestEmojiUser = firstEmoji.getEmojiUsers().stream()
                .map(emojiUser -> emojiUser.getUserId().getId())
                .toList();
        LoadEmojiResponse firstLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(firstEmoji.getEmojiId().getId())
                .emojiUsers(firstTestEmojiUser)
                .emojiType(firstEmoji.getEmojiType())
                .boardId(firstEmoji.getBoardId().getId())
                .build();
        List<Long> secondTestEmojiUser = secondEmoji.getEmojiUsers().stream()
                .map(emojiUser -> emojiUser.getUserId().getId())
                .toList();
        LoadEmojiResponse secondLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(secondEmoji.getEmojiId().getId())
                .emojiUsers(secondTestEmojiUser)
                .emojiType(secondEmoji.getEmojiType())
                .boardId(secondEmoji.getBoardId().getId())
                .build();
        List<Long> thirdTestEmojiUser = thirdEmoji.getEmojiUsers().stream()
                .map(emojiUser -> emojiUser.getUserId().getId())
                .toList();
        LoadEmojiResponse thirdLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(thirdEmoji.getEmojiId().getId())
                .emojiUsers(thirdTestEmojiUser)
                .emojiType(thirdEmoji.getEmojiType())
                .boardId(thirdEmoji.getBoardId().getId())
                .build();
        List<LoadEmojiResponse> loadEmojiResponses = List.of(firstLoadEmojiResponse, secondLoadEmojiResponse,
                thirdLoadEmojiResponse);
        List<Emoji> emojis = List.of(firstEmoji, secondEmoji, thirdEmoji);

        given(loadEmojisPort.loadEmojisByBoardId(BoardId.make(1L))).willReturn(emojis);

        LoadEmojisResponse loadEmojisResponse = emojiQueryService.loadByBoardId(BoardId.make(1L));

        assertEquals(loadEmojisResponse.getSearchEmojiResponses(), loadEmojiResponses);

        then(loadEmojisPort).should(times(1)).loadEmojisByBoardId(BoardId.make(1L));
    }
}