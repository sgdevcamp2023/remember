package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.mapper.ToSearchBoardResponseMapper;
import harmony.communityservice.community.mapper.ToSearchBoardsResponseMapper;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchBoardResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.repository.BoardQueryRepository;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.CommentQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {
    private static final int MAX_PAGE_COUNT = 50;
    private final BoardQueryRepository boardQueryRepository;
    private final CommentQueryService commentQueryService;


    @Override
    public List<SearchBoardResponse> searchList(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        return boardQueryRepository.findByChannelOrderByBoardId(channelId, lastBoardId, pageRequest)
                .stream()
                .map(b -> {
                    Long commentCount = commentQueryService.countingByBoardId(b.getBoardId());
                    return ToSearchBoardsResponseMapper.convert(b, commentCount);
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
        SearchCommentsResponse searchCommentsResponse = commentQueryService.searchListByBoardId(boardId);
        return ToSearchBoardResponseMapper.convert(targetBoard, searchCommentsResponse);

    }
}
