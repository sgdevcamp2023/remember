package harmony.communityservice.board.comment.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@EnableTransactionManagement
@Sql("CommentQueryPersistenceAdapterTest.sql")
class CommentQueryPersistenceAdapterTest {

    @Autowired
    CommentQueryPersistenceAdapter commentQueryPersistenceAdapter;

    @Autowired
    CommentQueryRepository commentQueryRepository;

    @Test
    @DisplayName("게시글안의 댓글 갯수 조회 테스트")
    void count_comments() {
        Long count = commentQueryPersistenceAdapter.count(BoardId.make(1L));
        Long entityCount = commentQueryRepository.countCommentsByBoardId(BoardIdJpaVO.make(1L));

        assertEquals(count,entityCount);
        assertEquals(count,4L);
    }

    @Test
    @DisplayName("게시글안의 댓글 조회 테스트")
    void load_comments() {
        List<Comment> comments = commentQueryPersistenceAdapter.loadByBoardId(BoardId.make(1L));
        List<CommentEntity> commentEntities = commentQueryRepository.findCommentsByBoardId(BoardIdJpaVO.make(1L));

        assertEquals(comments.size(),commentEntities.size());
        assertEquals(comments.size(),4L);
    }
}