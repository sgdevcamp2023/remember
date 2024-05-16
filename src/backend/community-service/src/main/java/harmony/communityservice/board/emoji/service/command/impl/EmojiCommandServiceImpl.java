package harmony.communityservice.board.emoji.service.command.impl;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;
import harmony.communityservice.board.emoji.mapper.ToEmojiMapper;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class EmojiCommandServiceImpl implements EmojiCommandService {

    private final EmojiCommandRepository emojiCommandRepository;

    @Override
    public void register(RegisterEmojiRequest registerEmojiRequest) {
        Emoji targetEmoji = emojiCommandRepository.findByBoardIdAndEmojiType(
                BoardId.make(registerEmojiRequest.boardId()),
                registerEmojiRequest.emojiType()).orElse(null);

        if (targetEmoji == null) {
            notExistsEmoji(registerEmojiRequest);
        } else {
            targetEmoji.exists(UserId.make(registerEmojiRequest.userId()));
        }
    }

    private void notExistsEmoji(RegisterEmojiRequest registerEmojiRequest) {
        Emoji emoji = ToEmojiMapper.convert(registerEmojiRequest.boardId(), registerEmojiRequest.emojiType(),
                registerEmojiRequest.userId());
        emojiCommandRepository.save(emoji);
    }

    @Override
    public void delete(DeleteEmojiRequest deleteEmojiRequest) {
        emojiCommandRepository.deleteById(EmojiId.make(deleteEmojiRequest.emojiId()));
    }

    @Override
    public void deleteListByBoardId(BoardId boardId) {
        emojiCommandRepository.deleteListByBoardId(boardId);
    }

    @Override
    public void deleteListByBoardIds(List<BoardId> boardIds) {
        emojiCommandRepository.deleteListByBoardIds(boardIds);
    }
}
