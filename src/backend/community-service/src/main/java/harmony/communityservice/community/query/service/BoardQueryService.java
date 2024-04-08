package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchBoardResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface BoardQueryService {

    List<SearchBoardResponse> searchList(long channelId, long lastBoardId);

    Board searchByBoardId(Long boardId);

    SearchBoardDetailResponse searchDetail(Long boardId);

}
