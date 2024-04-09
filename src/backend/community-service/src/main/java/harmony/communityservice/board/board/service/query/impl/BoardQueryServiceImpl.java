package harmony.communityservice.board.board.service.query.impl;

import harmony.communityservice.board.board.dto.SearchBoardDetailResponse;
import harmony.communityservice.board.board.dto.SearchBoardResponse;
import harmony.communityservice.board.board.mapper.ToSearchBoardResponseMapper;
import harmony.communityservice.board.board.mapper.ToSearchBoardsResponseMapper;
import harmony.communityservice.board.board.repository.query.BoardQueryRepository;
import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.board.comment.dto.SearchCommentResponse;
import harmony.communityservice.board.comment.dto.SearchCommentsResponse;
import harmony.communityservice.board.comment.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.board.domain.Board;
import harmony.communityservice.board.domain.Comment;
import harmony.communityservice.board.emoji.dto.SearchEmojisResponse;
import harmony.communityservice.board.emoji.service.query.EmojiQueryService;
import harmony.communityservice.common.exception.NotFoundDataException;
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


    @Override
    public List<SearchBoardResponse> searchList(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        return boardQueryRepository.findByChannelOrderByBoardId(channelId, lastBoardId, pageRequest)
                .stream()
                .map(b -> {
                    Long commentCount = b.countingComments();
                    SearchEmojisResponse searchEmojisResponse = emojiQueryService.searchListByBoardId(b.getBoardId());
                    return ToSearchBoardsResponseMapper.convert(b, commentCount, searchEmojisResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Board searchByBoardId(Long boardId) {
        return boardQueryRepository.findByBoardId(boardId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public SearchBoardDetailResponse searchDetail(Long boardId) {
        Board targetBoard = searchByBoardId(boardId);
        SearchCommentsResponse searchCommentsResponse = createSearchCommentsResponse(targetBoard);
        SearchEmojisResponse searchEmojisResponse = emojiQueryService.searchListByBoardId(boardId);
        return ToSearchBoardResponseMapper.convert(targetBoard, searchCommentsResponse, searchEmojisResponse);
    }

    private SearchCommentsResponse createSearchCommentsResponse(Board targetBoard) {
        List<Comment> comments = targetBoard.getComments();
        List<SearchCommentResponse> searchCommentResponses = comments.stream()
                .map(c -> ToSearchCommentResponseMapper.convert(c, targetBoard.getBoardId(), comments.indexOf(c)))
                .toList();
        return new SearchCommentsResponse(searchCommentResponses);
    }


}
