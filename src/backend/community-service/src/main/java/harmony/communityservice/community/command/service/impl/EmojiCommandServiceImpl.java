package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.community.command.dto.DeleteEmojiRequest;
import harmony.communityservice.community.command.dto.RegisterEmojiRequest;
import harmony.communityservice.community.command.repository.EmojiCommandRepository;
import harmony.communityservice.community.command.service.EmojiCommandService;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.mapper.ToEmojiMapper;
import harmony.communityservice.community.query.service.EmojiQueryService;
import lombok.RequiredArgsConstructor;

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
        } else {
            existsEmoji(registerEmojiRequest, targetEmoji);
        }
    }

    private void existsEmoji(RegisterEmojiRequest registerEmojiRequest, Emoji targetEmoji) {
        targetEmoji
                .exist(registerEmojiRequest.userId())
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        targetEmoji.updateUserIds(registerEmojiRequest.userId());
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
