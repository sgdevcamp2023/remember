package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BoardQueryService {

    List<BoardResponseDto> findBoards(long channelId, long lastBoardId);

    Board findBoardByBoardId(Long boardId);
}
