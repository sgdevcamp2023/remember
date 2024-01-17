package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.EmojiDeleteRequestDto;
import harmony.communityservice.community.command.dto.EmojiRegistrationRequestDto;
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
    public void save(EmojiRegistrationRequestDto emojiRegistrationRequestDto) {
        Board findBoard = boardQueryService.findBoardByBoardId(emojiRegistrationRequestDto.getBoardId());
        Emoji findEmoji = emojiQueryService.findByBoardAndEmojiType(findBoard,
                emojiRegistrationRequestDto.getEmojiType());
        if (findEmoji == null) {
            notExistsEmoji(emojiRegistrationRequestDto, findBoard);
        } else {
            existsEmoji(emojiRegistrationRequestDto, findEmoji);
        }
    }

    @Override
    public void delete(EmojiDeleteRequestDto requestDto) {
        Emoji findEmoji = emojiQueryService.findById(requestDto.getEmojiId());
        findEmoji.getEmojiUsers().stream()
                .filter(emojiUser -> Objects.equals(emojiUser.getUserId(), requestDto.getUserId()))
                .findAny()
                .ifPresent(emojiUserCommandService::delete);
    }

    private void existsEmoji(EmojiRegistrationRequestDto emojiRegistrationRequestDto, Emoji findEmoji) {
        findEmoji.getEmojiUsers().stream()
                .filter(emojiUser -> Objects.equals(emojiUser.getUserId(), emojiRegistrationRequestDto.getUserId()))
                .findAny()
                .ifPresent(e -> {
                    throw new IllegalStateException();
                });
        emojiUserCommandService.save(findEmoji, emojiRegistrationRequestDto.getUserId());
    }

    private void notExistsEmoji(EmojiRegistrationRequestDto emojiRegistrationRequestDto, Board findBoard) {
        Emoji emoji = ToEmojiMapper.convert(findBoard, emojiRegistrationRequestDto.getEmojiType());
        emojiCommandRepository.save(emoji);
        emojiUserCommandService.save(emoji, emojiRegistrationRequestDto.getUserId());
    }
}
