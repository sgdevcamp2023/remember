package harmony.communityservice.board.board.repository.query.impl;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.repository.query.BoardQueryRepository;
import harmony.communityservice.board.board.repository.query.jpa.JpaBoardQueryRepository;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.guild.channel.domain.ChannelId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JpaBoardQueryRepository jpaBoardQueryRepository;

    @Override
    public List<Board> findByChannelOrderByBoardId(ChannelId channelId, BoardId lastBoardId, Pageable pageable) {
        return jpaBoardQueryRepository.findBoardsByChannelOrderByBoardIdDesc(channelId, lastBoardId, pageable);
    }

    @Override
    public Optional<Board> findByBoardId(BoardId boardId) {
        return jpaBoardQueryRepository.findById(boardId);
    }
}
