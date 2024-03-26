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
        Board findBoard = boardQueryService.searchByBoardId(registerEmojiRequest.getBoardId());
        Emoji findEmoji = emojiQueryService.searchByBoardAndEmojiType(findBoard,
                registerEmojiRequest.getEmojiType());
        if (findEmoji == null) {
            notExistsEmoji(registerEmojiRequest, findBoard);
        } else {
            existsEmoji(registerEmojiRequest, findEmoji);
        }
    }

    @Override
    public void delete(DeleteEmojiRequest deleteEmojiRequest) {
        Emoji findEmoji = emojiQueryService.searchByEmojiId(deleteEmojiRequest.getEmojiId());
        findEmoji.getEmojiUsers().stream()
                .filter(emojiUser -> Objects.equals(emojiUser.getUserId(), deleteEmojiRequest.getUserId()))
                .findAny()
                .ifPresent(emojiUserCommandService::delete);
    }

    private void existsEmoji(RegisterEmojiRequest registerEmojiRequest, Emoji findEmoji) {
        findEmoji.getEmojiUsers().stream()
                .filter(emojiUser -> Objects.equals(emojiUser.getUserId(), registerEmojiRequest.getUserId()))
                .findAny()
                .ifPresent(e -> {
                    throw new DuplicatedEmojiException();
                });
        emojiUserCommandService.register(findEmoji, registerEmojiRequest.getUserId());
    }

    private void notExistsEmoji(RegisterEmojiRequest registerEmojiRequest, Board findBoard) {
        Emoji emoji = ToEmojiMapper.convert(findBoard, registerEmojiRequest.getEmojiType());
        emojiCommandRepository.save(emoji);
        emojiUserCommandService.register(emoji, registerEmojiRequest.getUserId());
    }
}
