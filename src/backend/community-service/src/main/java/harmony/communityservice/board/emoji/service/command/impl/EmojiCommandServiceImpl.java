package harmony.communityservice.board.emoji.service.command.impl;

import harmony.communityservice.board.emoji.dto.DeleteEmojiRequest;
import harmony.communityservice.board.emoji.dto.RegisterEmojiRequest;
import harmony.communityservice.board.emoji.repository.command.EmojiCommandRepository;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.board.domain.Emoji;
import harmony.communityservice.board.emoji.mapper.ToEmojiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class EmojiCommandServiceImpl implements EmojiCommandService {

    private final EmojiCommandRepository emojiCommandRepository;
    private final EmojiQueryService emojiQueryService;

    @Override
    public void register(RegisterEmojiRequest registerEmojiRequest) {
        Emoji targetEmoji = emojiQueryService.searchByBoardIdAndEmojiType(registerEmojiRequest.boardId(),
                registerEmojiRequest.emojiType());

        if (targetEmoji == null) {
            notExistsEmoji(registerEmojiRequest);
        }else{
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
        Emoji targetEmoji = emojiQueryService.searchByEmojiId(deleteEmojiRequest.emojiId());
        targetEmoji.deleteUserId(deleteEmojiRequest.userId());
    }
}
