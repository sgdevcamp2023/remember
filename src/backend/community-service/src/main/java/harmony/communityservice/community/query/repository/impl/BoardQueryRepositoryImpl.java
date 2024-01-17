package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.query.repository.BoardQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaBoardQueryRepository;
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
