package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.mapper.ToBoardResponseDtoMapper;
import harmony.communityservice.community.mapper.ToEmojiResponseDtoMapper;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.EmojiResponseDto;
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
    public List<BoardResponseDto> findBoards(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);

        return boardQueryRepository.findByChannelOrderByBoardId(channelId, lastBoardId, pageRequest)
                .stream()
                .map(findBoard -> {
                    List<EmojiResponseDto> emojiResponseDtos = findBoard.getEmojis().stream()
                            .map(ToEmojiResponseDtoMapper::convert)
                            .collect(Collectors.toList());
                    return ToBoardResponseDtoMapper.convert(findBoard, emojiResponseDtos);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Board findBoardByBoardId(Long boardId) {
        return boardQueryRepository.findByBoardId(boardId).orElseThrow();
    }
}
