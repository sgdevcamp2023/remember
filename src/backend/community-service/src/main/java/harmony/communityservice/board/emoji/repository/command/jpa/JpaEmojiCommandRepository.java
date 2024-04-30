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
    @Query(value = "delete from emoji_user as eu where eu.emoji_id in "
            + "(select e.emoji_id from emoji as e "
            + "where e.board_id = :boardId)", nativeQuery = true)
    void deleteEmojiUsersByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("delete from Emoji e where e.boardId  = :boardId")
    void deleteEmojisByBoardId(@Param("boardId") BoardId boardId);

    @Modifying
    @Query(value = "delete from emoji_user as eu where eu.emoji_id in "
            + "(select e.emoji_id from emoji as e "
            + "where e.board_id in :boardIds)",nativeQuery = true)
    void deleteEmojiUsersByBoardIds(@Param("boardIds") List<Long> boardIds);

    @Modifying
    @Query("delete from Emoji e where e.boardId in :boardIds")
    void deleteEmojisByBoardIds(@Param("boardIds") List<BoardId> boardIds);

}
