package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.exception.DuplicatedEmojiException;
import harmony.communityservice.community.command.dto.DeleteEmojiRequest;
import harmony.communityservice.community.command.dto.RegisterEmojiRequest;
import harmony.communityservice.community.command.repository.EmojiCommandRepository;
import harmony.communityservice.community.command.service.EmojiCommandService;
import harmony.communityservice.community.command.service.EmojiUserCommandService;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.mapper.ToEmojiMapper;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.EmojiQueryService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmojiCommandServiceImpl implements EmojiCommandService {

    private final BoardQueryService boardQueryService;
    private final EmojiCommandRepository emojiCommandRepository;
    private final EmojiUserCommandService emojiUserCommandService;
    private final EmojiQueryService emojiQueryService;

    @Override
    public void register(RegisterEmojiRequest registerEmojiRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(registerEmojiRequest.getBoardId());
        Emoji targetEmoji = emojiQueryService.searchByBoardAndEmojiType(targetBoard,
                registerEmojiRequest.getEmojiType());
        if (targetEmoji == null) {
            notExistsEmoji(registerEmojiRequest, targetBoard);
        } else {
            existsEmoji(registerEmojiRequest, targetEmoji);
        }
    }

    @Override
    public void delete(DeleteEmojiRequest deleteEmojiRequest) {
        Emoji targetEmoji = emojiQueryService.searchByEmojiId(deleteEmojiRequest.getEmojiId());
        targetEmoji.getEmojiUsers().stream()
                .filter(user -> Objects.equals(user.getUserId(), deleteEmojiRequest.getUserId()))
                .findAny()
                .ifPresent(emojiUserCommandService::delete);
    }

    private void existsEmoji(RegisterEmojiRequest registerEmojiRequest, Emoji targetEmoji) {
        targetEmoji.getEmojiUsers().stream()
                .filter(user -> Objects.equals(user.getUserId(), registerEmojiRequest.getUserId()))
                .findAny()
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        emojiUserCommandService.register(targetEmoji, registerEmojiRequest.getUserId());
    }

    private void notExistsEmoji(RegisterEmojiRequest registerEmojiRequest, Board targetBoard) {
        Emoji emoji = ToEmojiMapper.convert(targetBoard, registerEmojiRequest.getEmojiType());
        emojiCommandRepository.save(emoji);
        emojiUserCommandService.register(emoji, registerEmojiRequest.getUserId());
    }
}
