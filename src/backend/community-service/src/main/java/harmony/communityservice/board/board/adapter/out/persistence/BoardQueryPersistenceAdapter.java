package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.application.port.out.LoadBoardPort;
import harmony.communityservice.board.board.application.port.out.LoadBoardsPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@PersistenceAdapter
@RequiredArgsConstructor
class BoardQueryPersistenceAdapter implements LoadBoardsPort, LoadBoardPort {

    private final BoardQueryRepository boardQueryRepository;


    @Override
    public List<Board> loadBoards(ChannelId channelId, BoardId boardId, PageRequest pageRequest) {
        Page<BoardEntity> boardEntities = boardQueryRepository.findBoardsByChannelOrderByBoardIdDesc(
                ChannelIdJpaVO.make(channelId.getId()),
                BoardIdJpaVO.make(boardId.getId()), pageRequest);

        return boardEntities.getContent().stream()
                .map(BoardMapper::convert)
                .toList();
    }

    @Override
    public Board load(BoardId boardId) {
        BoardEntity boardEntity = boardQueryRepository.findById(BoardIdJpaVO.make(boardId.getId()))
                .orElseThrow(NotFoundDataException::new);
        return BoardMapper.convert(boardEntity);
    }
}
