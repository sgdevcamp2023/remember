package harmony.communityservice.board.emoji.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiCommand;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojisPort;
import harmony.communityservice.board.emoji.application.port.out.RegisterEmojiPort;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmojiCommandServiceTest {

    @Mock
    RegisterEmojiPort registerEmojiPort;
    @Mock DeleteEmojiPort deleteEmojiPort;
    @Mock DeleteEmojisPort deleteEmojisPort;
    EmojiCommandService emojiCommandService;

    @BeforeEach
    void setting() {
        emojiCommandService = new EmojiCommandService(registerEmojiPort, deleteEmojiPort, deleteEmojisPort);
    }

    @Test
    @DisplayName("이모지 등록 테스트")
    void register_emoji() {
        assertNotNull(emojiCommandService);
        willDoNothing().given(registerEmojiPort).register(BoardId.make(1L), UserId.make(1L),1L);

        emojiCommandService.register(new RegisterEmojiCommand(1L, 1L, 1L));

        then(registerEmojiPort).should(times(1)).register(BoardId.make(1L), UserId.make(1L),1L);
    }

    @Test
    @DisplayName("이모지 삭제 테스트")
    void delete_emoji() {
        assertNotNull(emojiCommandService);
        willDoNothing().given(deleteEmojiPort).delete(EmojiId.make(1L));

        emojiCommandService.delete(new DeleteEmojiCommand(1L, 1L));

        then(deleteEmojiPort).should(times(1)).delete(EmojiId.make(1L));
    }

    @Test
    @DisplayName("게시글 안의 이모지 삭제 테스트")
    void delete_board_emojis() {
        assertNotNull(emojiCommandService);
        willDoNothing().given(deleteEmojisPort).deleteByBoardId(BoardId.make(1L));

        emojiCommandService.deleteByBoardId(BoardId.make(1L));

        then(deleteEmojisPort).should(times(1)).deleteByBoardId(BoardId.make(1L));
    }

    @Test
    @DisplayName("채널 안의 이모지 삭제 테스트")
    void delete_channel_emojis() {
        assertNotNull(emojiCommandService);
        willDoNothing().given(deleteEmojisPort).deleteByBoardIds(List.of(BoardId.make(1L),BoardId.make(2L),BoardId.make(3L)));

        emojiCommandService.deleteByBoardIds(List.of(BoardId.make(1L),BoardId.make(2L),BoardId.make(3L)));

        then(deleteEmojisPort).should(times(1)).deleteByBoardIds(List.of(BoardId.make(1L),BoardId.make(2L),BoardId.make(3L)));
    }
}