package harmony.communityservice.board.emoji.repository.command.jpa;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiId;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaEmojiCommandRepository extends JpaRepository<Emoji, EmojiId> {

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    Optional<Emoji> findByBoardIdAndEmojiType(BoardId boardId, Long emojiType);

    @Modifying
    @Query("delete from Emoji e where e.boardId = :boardId")
    void deleteEmojisByBoardId(@Param("boardId") BoardId boardId);

    @Modifying
    @Query("delete from Emoji e where e.boardId in :boardIds")
    void deleteAllByBoardIds(@Param("boardIds") List<BoardId> boardIds);
}
