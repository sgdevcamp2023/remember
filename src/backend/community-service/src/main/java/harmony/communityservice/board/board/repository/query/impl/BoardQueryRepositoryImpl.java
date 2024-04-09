package harmony.communityservice.board.board.repository.query.impl;

import harmony.communityservice.board.board.repository.query.BoardQueryRepository;
import harmony.communityservice.board.board.repository.query.jpa.JpaBoardQueryRepository;
import harmony.communityservice.board.domain.Board;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JpaBoardQueryRepository jpaBoardQueryRepository;

    @Override
    public List<Board> findByChannelOrderByBoardId(Long channelId, Long lastBoardId, Pageable pageable) {
        return jpaBoardQueryRepository.findBoardsByChannelOrderByBoardIdDesc(channelId, lastBoardId, pageable);
    }

    @Override
    public Optional<Board> findByBoardId(Long boardId) {
        return jpaBoardQueryRepository.findById(boardId);
    }
}
