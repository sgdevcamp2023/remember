package harmony.communityservice.board.emoji.service.command.impl;

import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;
import harmony.communityservice.board.emoji.mapper.ToEmojiMapper;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class EmojiCommandServiceImpl implements EmojiCommandService {

    private final EmojiCommandRepository emojiCommandRepository;

    @Override
    public void register(RegisterEmojiRequest registerEmojiRequest) {
        Emoji targetEmoji = emojiCommandRepository.findByBoardIdAndEmojiType(registerEmojiRequest.boardId(),
                registerEmojiRequest.emojiType()).orElse(null);

        if (targetEmoji == null) {
            notExistsEmoji(registerEmojiRequest);
        } else {
            targetEmoji.exists(registerEmojiRequest.userId());
        }
    }

    private void notExistsEmoji(RegisterEmojiRequest registerEmojiRequest) {
        Emoji emoji = ToEmojiMapper.convert(registerEmojiRequest.boardId(), registerEmojiRequest.emojiType(),
                registerEmojiRequest.userId());
        emojiCommandRepository.save(emoji);
    }

    @Override
    public void delete(DeleteEmojiRequest deleteEmojiRequest) {
        emojiCommandRepository.deleteById(deleteEmojiRequest.emojiId());
    }

    @Override
    public void deleteListByBoardId(Long boardId) {
        emojiCommandRepository.deleteListByBoardId(boardId);
    }

    @Override
    public void deleteListByBoardIds(List<Long> boardIds) {
        emojiCommandRepository.deleteListByBoardIds(boardIds);
    }
}
