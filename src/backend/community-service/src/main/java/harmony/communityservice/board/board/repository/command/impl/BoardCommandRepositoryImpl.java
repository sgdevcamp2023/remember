package harmony.communityservice.board.board.repository.command.impl;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
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
    public Optional<Board> findById(Long boardId) {
        return jpaBoardCommandRepository.findById(boardId);
    }

    @Override
    public List<Long> findBoardIdsByChannelId(Long channelId) {
        return jpaBoardCommandRepository.findIdsByChannelId(channelId);
    }

    @Override
    public void deleteByChannelId(Long channelId) {
        jpaBoardCommandRepository.deleteBoardsByChannelId(channelId);
    }

    @Override
    public void deleteAllByChannelIds(List<Long> channelIds) {
        jpaBoardCommandRepository.deleteAllByChannelIds(channelIds);
    }

    @Override
    public List<Long> findAllByChannelIds(List<Long> channelIds) {
        return jpaBoardCommandRepository.findAllByChannelIds(channelIds);
    }
}
