package harmony.communityservice.board.comment.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.user.domain.User.UserId;
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
@Sql("CommentCommandPersistenceAdapterTest.sql")
class CommentCommandPersistenceAdapterTest {

    @Autowired
    CommentCommandPersistenceAdapter commentCommandPersistenceAdapter;

    @Autowired
    CommentCommandRepository commentCommandRepository;

    @Test
    @DisplayName("댓글 저장 테스트")
    void register_test() {
        Comment comment = Comment.builder()
                .comment("first_comment")
                .profile("https://user.cdn.com/test")
                .boardId(BoardId.make(1L))
                .nickname("test_user")
                .writerId(1L)
                .build();

        commentCommandPersistenceAdapter.register(comment);
        CommentEntity commentEntity = commentCommandRepository.findById(CommentIdJpaVO.make(9L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(9L, commentEntity.getId().getId());
        assertEquals(comment.getComment(), commentEntity.getComment());
        assertEquals(comment.getWriterInfo().getWriterId(), commentEntity.getWriterInfo().getWriterId());
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void modify_comment() {
        commentCommandPersistenceAdapter.modify(CommentId.make(1L), "modify comment", UserId.make(1L));

        CommentEntity commentEntity = commentCommandRepository.findById(CommentIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new);

        assertEquals(commentEntity.getComment(), "modify comment");
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delete_comment() {
        commentCommandPersistenceAdapter.delete(CommentId.make(1L));

        assertThrows(NotFoundDataException.class, () -> commentCommandRepository.findById(CommentIdJpaVO.make(1L))
                .orElseThrow(NotFoundDataException::new));
    }

    @Test
    @DisplayName("게시글 안의 댓글 삭제 테스트")
    void delete_comments() {
        List<CommentEntity> commentEntities = commentCommandRepository.findAll()
                .stream()
                .filter(commentEntity -> commentEntity.getBoardId().getId().equals(1L))
                .toList();
        commentCommandPersistenceAdapter.deleteByBoardId(BoardId.make(1L));
        List<CommentEntity> result = commentCommandRepository.findAll()
                .stream()
                .filter(commentEntity -> commentEntity.getBoardId().getId().equals(1L))
                .toList();

        assertThat(commentEntities.size()).isNotZero();
        assertThat(result.size()).isZero();
    }

    @Test
    @DisplayName("채널 안의 댓글 삭제 테스트")
    void delete_channel_comments() {
        List<BoardId> boardIds = List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L), BoardId.make(4L),
                BoardId.make(5L));

        List<CommentEntity> commentEntities = commentCommandRepository.findAll()
                .stream()
                .filter(commentEntity -> boardIds.stream()
                        .anyMatch(boardId -> boardId.getId().equals(commentEntity.getBoardId().getId())))
                .toList();
        commentCommandPersistenceAdapter.deleteByBoardIds(boardIds);
        List<CommentEntity> result = commentCommandRepository.findAll()
                .stream()
                .filter(commentEntity -> boardIds.stream()
                        .anyMatch(boardId -> boardId.getId().equals(commentEntity.getBoardId().getId())))
                .toList();

        assertThat(commentEntities.size()).isNotZero();
        assertThat(result.size()).isZero();
    }


}