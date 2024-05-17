package harmony.communityservice.board.board.repository.command.impl;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardCommandRepositoryImpl implements BoardCommandRepository {

    private final JpaBoardCommandRepository jpaBoardCommandRepository;

    @Override
    public void save(Board board) {
        jpaBoardCommandRepository.save(board);
    }

    @Override
    public void delete(Board board) {
        jpaBoardCommandRepository.delete(board);
    }

    @Override
    public Optional<Board> findById(BoardId boardId) {
        return jpaBoardCommandRepository.findById(boardId);
    }

    @Override
    public List<BoardId> findBoardIdsByChannelId(ChannelIdJpaVO channelId) {
        return jpaBoardCommandRepository.findIdsByChannelId(channelId);
    }

    @Override
    public void deleteByChannelId(ChannelIdJpaVO channelId) {
        jpaBoardCommandRepository.deleteImagesByChannelId(channelId.getId());
        jpaBoardCommandRepository.deleteBoardsByChannelId(channelId);
    }

    @Override
    public void deleteAllByChannelIds(List<ChannelIdJpaVO> channelIds) {
        jpaBoardCommandRepository.deleteImagesByChannelIds(channelIds.stream().map(ChannelIdJpaVO::getId).toList());
        jpaBoardCommandRepository.deleteAllByChannelIds(channelIds);
    }

    @Override
    public List<BoardId> findAllByChannelIds(List<ChannelIdJpaVO> channelIds) {
        return jpaBoardCommandRepository.findAllByChannelIds(channelIds);
    }
}
