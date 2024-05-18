package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.adapter.out.persistence.BoardEntity;
import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.dto.SearchBoardDetailResponse;
import harmony.communityservice.board.board.dto.SearchBoardResponse;
import harmony.communityservice.board.board.mapper.ToSearchBoardResponseMapper;
import harmony.communityservice.board.board.mapper.ToSearchBoardsResponseMapper;
import harmony.communityservice.board.board.repository.query.BoardQueryRepository;
import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.comment.service.query.CommentQueryService;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardQueryServiceImpl implements BoardQueryService {
    private static final int MAX_PAGE_COUNT = 50;
    private final BoardQueryRepository boardQueryRepository;
    private final EmojiQueryService emojiQueryService;
    private final CommentQueryService commentQueryService;


    @Override
    public List<SearchBoardResponse> searchList(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        return boardQueryRepository.findByChannelOrderByBoardId(ChannelIdJpaVO.make(channelId), BoardIdJpaVO.make(lastBoardId),
                        pageRequest)
                .stream()
                .map(b -> {
                    Long commentCount = commentQueryService.countingByBoardId(b.getBoardId().getId());
                    SearchEmojisResponse searchEmojisResponse = emojiQueryService.searchListByBoardId(
                            b.getBoardId().getId());
                    return ToSearchBoardsResponseMapper.convert(b, commentCount, searchEmojisResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public BoardEntity searchByBoardId(Long boardId) {
        return boardQueryRepository.findByBoardId(BoardIdJpaVO.make(boardId)).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public SearchBoardDetailResponse searchDetail(Long boardId) {
        BoardEntity targetBoard = searchByBoardId(boardId);
        SearchCommentsResponse searchCommentsResponse = createSearchCommentsResponse(targetBoard);
        SearchEmojisResponse searchEmojisResponse = emojiQueryService.searchListByBoardId(boardId);
        return ToSearchBoardResponseMapper.convert(targetBoard, searchCommentsResponse, searchEmojisResponse);
    }

    private SearchCommentsResponse createSearchCommentsResponse(BoardEntity targetBoard) {
        return commentQueryService.searchListByBoardId(targetBoard.getBoardId().getId());
    }
}
