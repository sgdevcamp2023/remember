package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.mapper.ToSearchBoardResponseMapper;
import harmony.communityservice.community.mapper.ToSearchBoardsResponseMapper;
import harmony.communityservice.community.mapper.ToSearchCommentResponseMapper;
import harmony.communityservice.community.mapper.ToSearchEmojiResponseMapper;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchBoardResponse;
import harmony.communityservice.community.query.dto.SearchCommentResponse;
import harmony.communityservice.community.query.dto.SearchCommentsResponse;
import harmony.communityservice.community.query.dto.SearchEmojiResponse;
import harmony.communityservice.community.query.dto.SearchEmojisResponse;
import harmony.communityservice.community.query.dto.SearchImageResponse;
import harmony.communityservice.community.query.dto.SearchImagesResponse;
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

    private static SearchImagesResponse makeSearchImagesResponse(Board findBoard) {
        return new SearchImagesResponse(
                findBoard.getImages().stream()
                        .map(image -> new SearchImageResponse(image.getImageAddr()))
                        .collect(Collectors.toList())
        );
    }

    private static SearchEmojisResponse makeSearchEmojisResponse(Board findBoard) {
        List<Emoji> emojis = findBoard.getEmojis();
        return new SearchEmojisResponse(
                emojis.stream()
                        .map(ToSearchEmojiResponseMapper::convert)
                        .collect(Collectors.toList())
        );
    }

    private static SearchCommentsResponse makeSearchCommentsResponse(long boardId, Board findBoard) {
        List<SearchCommentResponse> searchCommentResponses = findBoard.getComments().stream()
                .map(comment -> ToSearchCommentResponseMapper.convert(comment, boardId))
                .collect(Collectors.toList());
        return new SearchCommentsResponse(searchCommentResponses);
    }

    @Override
    public List<SearchBoardResponse> searchList(long channelId, long lastBoardId) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);

        return boardQueryRepository.findByChannelOrderByBoardId(channelId, lastBoardId, pageRequest)
                .stream()
                .map(findBoard -> {
                    List<SearchEmojiResponse> searchEmojiResponses = findBoard.getEmojis().stream()
                            .map(ToSearchEmojiResponseMapper::convert)
                            .collect(Collectors.toList());
                    return ToSearchBoardsResponseMapper.convert(findBoard, searchEmojiResponses);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Board searchByBoardId(Long boardId) {
        return boardQueryRepository.findByBoardId(boardId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public SearchBoardDetailResponse searchBoardDetail(long boardId) {
        Board targetBoard = searchByBoardId(boardId);
        SearchCommentsResponse searchCommentsResponse = makeSearchCommentsResponse(boardId,
                targetBoard);
        SearchEmojisResponse searchEmojisResponse = makeSearchEmojisResponse(targetBoard);
        SearchImagesResponse searchImagesResponse = makeSearchImagesResponse(targetBoard);
        return ToSearchBoardResponseMapper.convert(targetBoard, searchCommentsResponse, searchEmojisResponse,
                searchImagesResponse, boardId);
    }
}
