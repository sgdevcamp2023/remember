package harmony.communityservice.board.comment.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.willDoNothing;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.DeleteCommentCommand;
import harmony.communityservice.board.comment.application.port.in.ModifyCommentCommand;
import harmony.communityservice.board.comment.application.port.in.RegisterCommentCommand;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentPort;
import harmony.communityservice.board.comment.application.port.out.DeleteCommentsPort;
import harmony.communityservice.board.comment.application.port.out.ModifyCommentPort;
import harmony.communityservice.board.comment.application.port.out.RegisterCommentPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentCommandServiceTest {

    @Mock
    RegisterCommentPort registerCommentPort;
    @Mock
    ModifyCommentPort modifyCommentPort;
    @Mock
    DeleteCommentPort deleteCommentPort;
    @Mock
    DeleteCommentsPort deleteCommentsPort;
    CommentCommandService commentCommandService;

    @BeforeEach
    void setting() {
        commentCommandService = new CommentCommandService(registerCommentPort, modifyCommentPort, deleteCommentPort,
                deleteCommentsPort);
    }

    @Test
    @DisplayName("댓글 등록 테스트")
    void register_comment() {
        assertNotNull(commentCommandService);

        Comment comment = Comment.builder()
                .commentId(CommentId.make(Threshold.MIN.getValue()))
                .comment("first_comment")
                .profile("https://user.cdn.com/test")
                .boardId(BoardId.make(1L))
                .nickname("test_user")
                .writerId(1L)
                .build();

        willDoNothing().given(registerCommentPort).register(comment);
        RegisterCommentCommand registerCommentCommand = RegisterCommentCommand.builder()
                .boardId(1L)
                .userId(1L)
                .writerName("test_user")
                .comment("first_comment")
                .writerProfile("https://user.cdn.com/test")
                .build();

        commentCommandService.register(registerCommentCommand);

        then(registerCommentPort).should(times(1)).register(comment);
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void modify_comment() {
        assertNotNull(commentCommandService);

        willDoNothing().given(modifyCommentPort).modify(CommentId.make(1L), "modify_comment", UserId.make(1L));

        commentCommandService.modify(new ModifyCommentCommand(1L, 1L, 1L, "modify_comment"));

        then(modifyCommentPort).should(times(1)).modify(CommentId.make(1L), "modify_comment", UserId.make(1L));
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void delete_comment() {
        assertNotNull(commentCommandService);
        willDoNothing().given(deleteCommentPort).delete(CommentId.make(1L));

        commentCommandService.delete(new DeleteCommentCommand(1L, 1L, 1L));

        then(deleteCommentPort).should(times(1)).delete(CommentId.make(1L));
    }

    @Test
    @DisplayName("게시글 안의 댓글 삭제 테스트")
    void delete_board_comments() {
        assertNotNull(commentCommandService);
        willDoNothing().given(deleteCommentsPort).deleteByBoardId(BoardId.make(1L));

        commentCommandService.deleteByBoardId(BoardId.make(1L));

        then(deleteCommentsPort).should(times(1)).deleteByBoardId(BoardId.make(1L));
    }

    @Test
    @DisplayName("채널안의 댓글 삭제 테스트")
    void delete_boards_comments() {
        assertNotNull(commentCommandService);

        List<BoardId> boardIds = List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L));
        willDoNothing().given(deleteCommentsPort).deleteByBoardIds(boardIds);

        commentCommandService.deleteByBoardIds(boardIds);

        then(deleteCommentsPort).should(times(1)).deleteByBoardIds(boardIds);
    }
}