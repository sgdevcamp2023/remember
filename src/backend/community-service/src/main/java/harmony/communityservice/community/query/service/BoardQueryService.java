package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.BoardsResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface BoardQueryService {

    List<BoardsResponseDto> findBoards(long channelId, long lastBoardId);

    Board findBoardByBoardId(Long boardId);

    BoardResponseDto make(long boardId);
}
