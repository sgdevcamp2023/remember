package harmony.communityservice.board.board.repository.command.impl;

import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.repository.command.jpa.JpaBoardCommandRepository;
import harmony.communityservice.board.domain.Board;
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
}
