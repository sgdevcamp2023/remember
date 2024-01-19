package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.mapper.ToBoardResponseDtoMapper;
import harmony.communityservice.community.mapper.ToBoardsResponseDtoMapper;
import harmony.communityservice.community.mapper.ToCommentResponseDtoMapper;
import harmony.communityservice.community.mapper.ToEmojiResponseDtoMapper;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.BoardsResponseDto;
import harmony.communityservice.community.query.dto.CommentResponseDto;
import harmony.communityservice.community.query.dto.CommentsResponseDto;
import harmony.communityservice.community.query.dto.EmojiResponseDto;
import harmony.communityservice.community.query.dto.EmojisResponseDto;
import harmony.communityservice.community.query.dto.ImageResponseDto;
import harmony.communityservice.community.query.dto.ImagesResponseDto;
import harmony.communityservice.community.query.repository.BoardQueryRepository;
import harmony.communityservice.community.query.service.BoardQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {
    private static final int MAX_PAGE_COUNT = 50;
    private final BoardQueryRepository boardQueryRepository;

    @Override
    public List<BoardsResponseDto> findBoards(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);

        return boardQueryRepository.findByChannelOrderByBoardId(channelId, lastBoardId, pageRequest)
                .stream()
                .map(findBoard -> {
                    List<EmojiResponseDto> emojiResponseDtos = findBoard.getEmojis().stream()
                            .map(ToEmojiResponseDtoMapper::convert)
                            .collect(Collectors.toList());
                    return ToBoardsResponseDtoMapper.convert(findBoard, emojiResponseDtos);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Board findBoardByBoardId(Long boardId) {
        return boardQueryRepository.findByBoardId(boardId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public BoardResponseDto make(long boardId) {
        Board findBoard = findBoardByBoardId(boardId);
        CommentsResponseDto commentsResponseDto = makeCommentsResponseDto(boardId,
                findBoard);
        EmojisResponseDto emojisResponseDto = makeEmojiResponseDto(findBoard);
        ImagesResponseDto imagesResponseDto = makeImagesResponseDto(findBoard);
        return ToBoardResponseDtoMapper.convert(findBoard, commentsResponseDto, emojisResponseDto, imagesResponseDto,
                boardId);
    }

    private static ImagesResponseDto makeImagesResponseDto(Board findBoard) {
        return new ImagesResponseDto(
                findBoard.getImages().stream()
                        .map(image -> new ImageResponseDto(image.getImageAddr()))
                        .collect(Collectors.toList())
        );
    }

    private static EmojisResponseDto makeEmojiResponseDto(Board findBoard) {
        List<Emoji> emojis = findBoard.getEmojis();
        return new EmojisResponseDto(
                emojis.stream()
                        .map(ToEmojiResponseDtoMapper::convert)
                        .collect(Collectors.toList())
        );
    }

    private static CommentsResponseDto makeCommentsResponseDto(long boardId, Board findBoard) {
        List<CommentResponseDto> commentResponseDtos = findBoard.getComments().stream()
                .map(comment -> ToCommentResponseDtoMapper.convert(comment, boardId))
                .collect(Collectors.toList());
        return new CommentsResponseDto(commentResponseDtos);
    }
}
