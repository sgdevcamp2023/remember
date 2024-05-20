package harmony.communityservice.board.emoji.application.service;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojiUseCase;
import harmony.communityservice.board.emoji.application.port.in.DeleteEmojisUseCase;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiCommand;
import harmony.communityservice.board.emoji.application.port.in.RegisterEmojiUseCase;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojiPort;
import harmony.communityservice.board.emoji.application.port.out.DeleteEmojisPort;
import harmony.communityservice.board.emoji.application.port.out.RegisterEmojiPort;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
class EmojiCommandService implements RegisterEmojiUseCase, DeleteEmojiUseCase, DeleteEmojisUseCase {

    private final RegisterEmojiPort registerEmojiPort;
    private final DeleteEmojiPort deleteEmojiPort;
    private final DeleteEmojisPort deleteEmojisPort;

    @Override
    public void register(RegisterEmojiCommand registerEmojiCommand) {
        registerEmojiPort.register(BoardId.make(registerEmojiCommand.boardId()), UserId.make(
                registerEmojiCommand.userId()), registerEmojiCommand.emojiType());
    }

    @Override
    public void delete(DeleteEmojiCommand deleteEmojiCommand) {
        deleteEmojiPort.delete(EmojiId.make(deleteEmojiCommand.emojiId()));
    }

    @Override
    public void deleteByBoardId(BoardId boardId) {
        deleteEmojisPort.deleteByBoardId(boardId);
    }

    @Override
    public void deleteByBoardIds(List<BoardId> boardIds) {
        deleteEmojisPort.deleteByBoardIds(boardIds);
    }
}
