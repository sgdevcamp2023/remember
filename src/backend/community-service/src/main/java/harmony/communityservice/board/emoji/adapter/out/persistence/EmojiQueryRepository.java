package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface EmojiQueryRepository extends JpaRepository<EmojiEntity, EmojiIdJpaVO> {
    @Query("select distinct e from EmojiEntity e join fetch e.emojiUsers eu "
            + "where e.boardId = :boardId and e.emojiType = :emojiType")
    Optional<EmojiEntity> findEmojiByBoardIdAndEmojiType(@Param("boardId") BoardIdJpaVO boardId,
                                                         @Param("emojiType") Long emojiType);

    @Query("select distinct e from EmojiEntity e join fetch e.emojiUsers eu where e.emojiId = :emojiId")
    Optional<EmojiEntity> findByEmojiId(@Param("emojiId") EmojiIdJpaVO emojiId);

    @Query("select distinct e from EmojiEntity e join fetch e.emojiUsers eu where e.boardId = :boardId")
    List<EmojiEntity> findEmojisByBoardId(@Param("boardId") BoardIdJpaVO boardId);
}
