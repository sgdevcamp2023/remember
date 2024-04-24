package harmony.communityservice.board.board.service.query;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.dto.SearchBoardDetailResponse;
import harmony.communityservice.board.board.dto.SearchBoardResponse;
import java.util.List;

public interface BoardQueryService {

    List<SearchBoardResponse> searchList(long channelId, long lastBoardId);

    Board searchByBoardId(Long boardId);

    SearchBoardDetailResponse searchDetail(Long boardId);

}
