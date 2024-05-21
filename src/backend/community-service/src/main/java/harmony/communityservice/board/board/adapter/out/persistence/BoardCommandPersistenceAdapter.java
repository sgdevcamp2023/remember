package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.application.port.out.DeleteBoardPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelBoardsPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelsBoardsPort;
import harmony.communityservice.board.board.application.port.out.ModifyBoardPort;
import harmony.communityservice.board.board.application.port.out.RegisterBoardPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.common.annotation.PersistenceAdapter;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class BoardCommandPersistenceAdapter implements RegisterBoardPort, ModifyBoardPort, DeleteBoardPort,
        DeleteChannelBoardsPort, DeleteChannelsBoardsPort {

    private final BoardCommandRepository boardCommandRepository;

    @Override
    public void register(Board board) {
        BoardEntity boardEntity = BoardEntityMapper.convert(board);
        boardCommandRepository.save(boardEntity);
    }

    @Override
    public void modify(Long writerId, BoardId boardId, String title, String content) {
        boardCommandRepository.modifyContent(title, content, BoardIdJpaVO.make(boardId.getId()), writerId);
    }

    @Override
    public void delete(Long writerId, BoardId boardId) {
        boardCommandRepository.deleteByBoardId(BoardIdJpaVO.make(boardId.getId()), writerId);
        boardCommandRepository.deleteImagesByBoardId(boardId.getId());
    }

    @Override
    public List<BoardId> deleteChannelBoards(ChannelId channelId) {
        List<BoardId> boardIds = boardCommandRepository.findIdsByChannelId(ChannelIdJpaVO.make(channelId.getId()))
                .stream()
                .map(boardIdJpaVO -> BoardId.make(boardIdJpaVO.getId()))
                .toList();
        boardCommandRepository.deleteBoardsByChannelId(ChannelIdJpaVO.make(channelId.getId()));
        boardCommandRepository.deleteImagesByChannelId(channelId.getId());
        return boardIds;
    }

    @Override
    public List<BoardId> deleteInChannels(List<ChannelId> channelIds) {
        List<Long> ids = channelIds.stream()
                .map(ChannelId::getId)
                .toList();
        List<ChannelIdJpaVO> channelIdJpaVOS = channelIds.stream()
                .map(channelId -> ChannelIdJpaVO.make(channelId.getId()))
                .toList();
        List<BoardId> boardIds = boardCommandRepository.findAllByChannelIds(channelIdJpaVOS)
                .stream()
                .map(boardIdJpaVO -> BoardId.make(boardIdJpaVO.getId()))
                .toList();
        boardCommandRepository.deleteImagesByChannelIds(ids);
        boardCommandRepository.deleteAllByChannelIds(channelIdJpaVOS);

        return boardIds;
    }
}
