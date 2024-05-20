package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.application.port.in.LoadBoardDetailResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardQuery;
import harmony.communityservice.board.board.application.port.in.LoadBoardResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardsCommand;
import harmony.communityservice.board.board.application.port.in.LoadBoardsQuery;
import harmony.communityservice.board.board.application.port.in.LoadBoardsResponse;
import harmony.communityservice.board.board.application.port.out.LoadBoardPort;
import harmony.communityservice.board.board.application.port.out.LoadBoardsPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.CountCommentQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisQuery;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class BoardQueryService implements LoadBoardsQuery, LoadBoardQuery {
    private static final int MAX_PAGE_COUNT = 50;
    private final LoadBoardsPort loadBoardsPort;
    private final CountCommentQuery countCommentQuery;
    private final LoadEmojisQuery loadEmojisQuery;
    private final LoadBoardPort loadBoardPort;
    private final LoadCommentsQuery loadCommentsQuery;

    @Override
    public LoadBoardsResponse loadList(LoadBoardsCommand loadBoardsCommand) {
        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        List<LoadBoardResponse> loadBoardResponses = loadBoardsPort.loadBoards(
                        ChannelId.make(loadBoardsCommand.channelId()), BoardId.make(
                                loadBoardsCommand.lastBoardId()), pageRequest)
                .stream()
                .map(b -> {
                    Long commentCount = countCommentQuery.count(b.getBoardId());
                    LoadEmojisResponse searchEmojisResponse = loadEmojisQuery.loadByBoardId(b.getBoardId());
                    return LoadBoardResponseMapper.convert(b, commentCount, searchEmojisResponse);
                })
                .toList();
        return new LoadBoardsResponse(loadBoardResponses);
    }

    @Override
    public LoadBoardDetailResponse loadDetail(BoardId boardId) {
        Board board = loadBoardPort.load(boardId);
        LoadCommentsResponse loadCommentsResponse = loadCommentsQuery.load(boardId);
        LoadEmojisResponse loadEmojisResponse = loadEmojisQuery.loadByBoardId(boardId);
        return LoadBoardDetailResponseMapper.convert(board, loadCommentsResponse, loadEmojisResponse);
    }
}
