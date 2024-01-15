package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.BoardCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaBoardCommandRepository;
import harmony.communityservice.community.domain.Board;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardCommandRepositoryImpl implements BoardCommandRepository {

    private final JpaBoardCommandRepository jpaBoardCommandRepository;

    @Override
    public void save(Board board) {
        jpaBoardCommandRepository.save(board);
    }
}
