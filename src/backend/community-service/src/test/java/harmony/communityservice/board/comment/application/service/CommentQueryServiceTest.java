package harmony.communityservice.board.comment.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.comment.application.port.in.LordCommentResponse;
import harmony.communityservice.board.comment.application.port.out.CountCommentPort;
import harmony.communityservice.board.comment.application.port.out.LoadCommentsPort;
import harmony.communityservice.board.comment.domain.Comment;
import harmony.communityservice.board.comment.domain.Comment.CommentId;
import harmony.communityservice.board.comment.domain.ModifiedType;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentQueryServiceTest {

    @Mock
    CountCommentPort countCommentPort;
    @Mock
    LoadCommentsPort loadCommentsPort;
    CommentQueryService commentQueryService;

    @BeforeEach
    void setting() {
        commentQueryService = new CommentQueryService(countCommentPort, loadCommentsPort);
    }

    @Test
    @DisplayName("댓글 수 카운트 테스트")
    void count_comment() {
        assertNotNull(commentQueryService);

        given(countCommentPort.count(BoardId.make(1L))).willReturn(5L);

        Long count = commentQueryService.count(BoardId.make(1L));

        assertEquals(count, 5L);
        then(countCommentPort).should(times(1)).count(BoardId.make(1L));
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    void load_comment() {
        assertNotNull(commentQueryService);

        Comment first = Comment.builder()
                .nickname("test_user")
                .writerId(1L)
                .profile("https://user.cdn.com/test")
                .comment("first_comment")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .createdAt(Instant.now())
                .type(ModifiedType.valueOf("NOT_YET"))
                .build();
        Comment second = Comment.builder()
                .nickname("test_user")
                .writerId(1L)
                .profile("https://user.cdn.com/test")
                .comment("first_comment")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .createdAt(Instant.now())
                .type(ModifiedType.valueOf("NOT_YET"))
                .build();
        Comment third = Comment.builder()
                .nickname("test_user")
                .writerId(1L)
                .profile("https://user.cdn.com/test")
                .comment("first_comment")
                .commentId(CommentId.make(1L))
                .boardId(BoardId.make(1L))
                .createdAt(Instant.now())
                .type(ModifiedType.valueOf("NOT_YET"))
                .build();

        LordCommentResponse firstLoadCommentResponse = LordCommentResponse.builder()
                .commentId(first.getCommentId().getId())
                .comment(first.getComment())
                .writerName(first.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(first.getWriterInfo().getWriterId())
                .writerProfile(first.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(first.getType())
                .boardId(first.getBoardId().getId())
                .createdAt(first.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        LordCommentResponse secondLoadCommentResponse = LordCommentResponse.builder()
                .commentId(second.getCommentId().getId())
                .comment(second.getComment())
                .writerName(second.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(second.getWriterInfo().getWriterId())
                .writerProfile(second.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(second.getType())
                .boardId(second.getBoardId().getId())
                .createdAt(second.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        LordCommentResponse thirdLoadCommentResponse = LordCommentResponse.builder()
                .commentId(third.getCommentId().getId())
                .comment(third.getComment())
                .writerName(third.getWriterInfo().getCommonUserInfo().getNickname())
                .userId(third.getWriterInfo().getWriterId())
                .writerProfile(third.getWriterInfo().getCommonUserInfo().getProfile())
                .modified(third.getType())
                .boardId(third.getBoardId().getId())
                .createdAt(third.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        List<Comment> comments = List.of(first, second, third);
        List<LordCommentResponse> loadCommentResponses = List.of(firstLoadCommentResponse,
                secondLoadCommentResponse, thirdLoadCommentResponse);
        given(loadCommentsPort.loadByBoardId(BoardId.make(1L))).willReturn(comments);

        LoadCommentsResponse loadCommentsResponse = commentQueryService.load(BoardId.make(1L));

        assertEquals(loadCommentResponses, loadCommentsResponse.getSearchCommentResponses());
        then(loadCommentsPort).should(times(1)).loadByBoardId(BoardId.make(1L));
    }
}