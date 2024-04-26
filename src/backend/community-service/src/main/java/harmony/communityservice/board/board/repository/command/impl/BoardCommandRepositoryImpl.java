package harmony.communityservice.board.board.repository.command.impl;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
import harmony.communityservice.guild.channel.domain.ChannelId;
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
    public List<BoardId> findBoardIdsByChannelId(ChannelId channelId) {
        return jpaBoardCommandRepository.findIdsByChannelId(channelId);
    }

    @Override
    public void deleteByChannelId(ChannelId channelId) {
        jpaBoardCommandRepository.deleteBoardsByChannelId(channelId);
    }

    @Override
    public void deleteAllByChannelIds(List<ChannelId> channelIds) {
        jpaBoardCommandRepository.deleteAllByChannelIds(channelIds);
    }

    @Override
    public List<BoardId> findAllByChannelIds(List<ChannelId> channelIds) {
        return jpaBoardCommandRepository.findAllByChannelIds(channelIds);
    }
}
