package harmony.communityservice.board.board.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.application.port.in.LoadBoardDetailResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardsCommand;
import harmony.communityservice.board.board.application.port.in.LoadBoardsResponse;
import harmony.communityservice.board.board.application.port.in.LoadImageResponse;
import harmony.communityservice.board.board.application.port.in.LoadImagesResponse;
import harmony.communityservice.board.board.application.port.out.LoadBoardPort;
import harmony.communityservice.board.board.application.port.out.LoadBoardsPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.board.comment.application.port.in.CountCommentQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsQuery;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.comment.application.port.in.LordCommentResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojiResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisQuery;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class BoardQueryServiceTest {

    private static final int MAX_PAGE_COUNT = 50;

    @Mock
    LoadBoardsPort loadBoardsPort;
    @Mock
    CountCommentQuery countCommentQuery;
    @Mock
    LoadEmojisQuery loadEmojisQuery;
    @Mock
    LoadBoardPort loadBoardPort;
    @Mock
    LoadCommentsQuery loadCommentsQuery;
    BoardQueryService boardQueryService;

    @BeforeEach
    void setting() {
        boardQueryService = new BoardQueryService(loadBoardsPort, countCommentQuery, loadEmojisQuery, loadBoardPort,
                loadCommentsQuery);
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void load_boards() {
        assertNotNull(boardQueryService);

        PageRequest pageRequest = PageRequest.of(0, MAX_PAGE_COUNT);
        Image firstImage = Image.make(1L, "https://cdn.com/first");
        Image secondImage = Image.make(2L, "https://cdn.com/second");
        Image thirdImage = Image.make(3L, "https://cdn.com/third");
        List<Image> images = List.of(firstImage, secondImage, thirdImage);
        Board firstBoard = Board.builder()
                .boardId(BoardId.make(1L))
                .title("first_board")
                .content("first_content")
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("0chord")
                .profile("https://cdn.com/user1")
                .images(images)
                .createdAt(Instant.now())
                .type(ModifiedType.NOT_YET)
                .build();
        Board secondBoard = Board.builder()
                .boardId(BoardId.make(2L))
                .title("second_board")
                .content("first_content")
                .channelId(ChannelId.make(1L))
                .writerId(2L)
                .username("0Chord")
                .profile("https://cdn.com/user2")
                .images(images)
                .createdAt(Instant.now())
                .type(ModifiedType.NOT_YET)
                .build();
        Board thirdBoard = Board.builder()
                .boardId(BoardId.make(3L))
                .title("third_board")
                .content("third_content")
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("0chord")
                .profile("https://cdn.com/user1")
                .images(images)
                .createdAt(Instant.now())
                .type(ModifiedType.NOT_YET)
                .build();
        List<Long> firstEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse firstLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(1L)
                .emojiUsers(firstEmojiUser)
                .emojiType(1L)
                .boardId(1L)
                .build();
        List<Long> secondEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse secondLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(2L)
                .emojiUsers(secondEmojiUser)
                .emojiType(2L)
                .boardId(2L)
                .build();
        List<Long> thirdEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse thirdLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(3L)
                .emojiUsers(thirdEmojiUser)
                .emojiType(3L)
                .boardId(3L)
                .build();

        LoadBoardResponse firstLoadBoardResponse = LoadBoardResponse.builder()
                .boardId(firstBoard.getBoardId().getId())
                .commentCount(3L)
                .title(firstBoard.getContent().getTitle())
                .content(firstBoard.getContent().getContent())
                .userId(firstBoard.getWriterInfo().getWriterId())
                .writer(firstBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(firstBoard.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .channelId(firstBoard.getChannelId().getId())
                .modified(firstBoard.getType())
                .searchEmojiResponses(new LoadEmojisResponse(List.of(firstLoadEmojiResponse)))
                .build();

        LoadBoardResponse secondLoadBoardResponse = LoadBoardResponse.builder()
                .boardId(secondBoard.getBoardId().getId())
                .commentCount(4L)
                .title(secondBoard.getContent().getTitle())
                .content(secondBoard.getContent().getContent())
                .userId(secondBoard.getWriterInfo().getWriterId())
                .writer(secondBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(secondBoard.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .channelId(secondBoard.getChannelId().getId())
                .modified(secondBoard.getType())
                .searchEmojiResponses(new LoadEmojisResponse(List.of(secondLoadEmojiResponse)))
                .build();
        LoadBoardResponse thirdLoadBoardResponse = LoadBoardResponse.builder()
                .boardId(thirdBoard.getBoardId().getId())
                .commentCount(5L)
                .title(thirdBoard.getContent().getTitle())
                .content(thirdBoard.getContent().getContent())
                .userId(thirdBoard.getWriterInfo().getWriterId())
                .writer(thirdBoard.getWriterInfo().getCommonUserInfo().getNickname())
                .createdAt(thirdBoard.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .channelId(thirdBoard.getChannelId().getId())
                .modified(thirdBoard.getType())
                .searchEmojiResponses(new LoadEmojisResponse(List.of(thirdLoadEmojiResponse)))
                .build();

        List<LoadBoardResponse> loadBoardResponses = List.of(firstLoadBoardResponse, secondLoadBoardResponse,
                thirdLoadBoardResponse);
        List<Board> boards = List.of(firstBoard, secondBoard, thirdBoard);
        given(loadBoardsPort.loadBoards(ChannelId.make(1L), BoardId.make(1L), pageRequest)).willReturn(boards);
        given(countCommentQuery.count(BoardId.make(1L))).willReturn(3L);
        given(countCommentQuery.count(BoardId.make(2L))).willReturn(4L);
        given(countCommentQuery.count(BoardId.make(3L))).willReturn(5L);
        given(loadEmojisQuery.loadByBoardId(BoardId.make(1L))).willReturn(
                new LoadEmojisResponse(List.of(firstLoadEmojiResponse)));
        given(loadEmojisQuery.loadByBoardId(BoardId.make(2L))).willReturn(
                new LoadEmojisResponse(List.of(secondLoadEmojiResponse)));
        given(loadEmojisQuery.loadByBoardId(BoardId.make(3L))).willReturn(
                new LoadEmojisResponse(List.of(thirdLoadEmojiResponse)));

        LoadBoardsResponse loadBoardsResponse = boardQueryService.loadList(new LoadBoardsCommand(1L, 1L));

        assertEquals(loadBoardsResponse.loadBoardResponses(), loadBoardResponses);
        then(loadBoardsPort).should(times(1)).loadBoards(ChannelId.make(1L), BoardId.make(1L), pageRequest);
        then(countCommentQuery).should(times(1)).count(BoardId.make(1L));
        then(countCommentQuery).should(times(1)).count(BoardId.make(2L));
        then(countCommentQuery).should(times(1)).count(BoardId.make(3L));
        then(loadEmojisQuery).should(times(1)).loadByBoardId(BoardId.make(1L));
        then(loadEmojisQuery).should(times(1)).loadByBoardId(BoardId.make(2L));
        then(loadEmojisQuery).should(times(1)).loadByBoardId(BoardId.make(3L));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void load_board_detail() {
        assertNotNull(boardQueryService);

        Image firstImage = Image.make(1L, "https://cdn.com/first");
        Image secondImage = Image.make(2L, "https://cdn.com/second");
        Image thirdImage = Image.make(3L, "https://cdn.com/third");
        List<Image> images = List.of(firstImage, secondImage, thirdImage);
        Board board = Board.builder()
                .boardId(BoardId.make(1L))
                .title("first_board")
                .content("first_content")
                .channelId(ChannelId.make(1L))
                .writerId(1L)
                .username("0chord")
                .profile("https://cdn.com/user1")
                .images(images)
                .createdAt(Instant.now())
                .type(ModifiedType.NOT_YET)
                .build();

        LordCommentResponse firstLoadCommentResponse = LordCommentResponse.builder()
                .commentId(1L)
                .comment("first comment")
                .writerName("0chord")
                .userId(1L)
                .writerProfile("https://cdn.com/user1")
                .modified(harmony.communityservice.board.comment.domain.ModifiedType.NOT_YET)
                .boardId(1L)
                .createdAt(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        LordCommentResponse secondLoadCommentResponse = LordCommentResponse.builder()
                .commentId(2L)
                .comment("second comment")
                .writerName("0Chord")
                .userId(2L)
                .writerProfile("https://cdn.com/user2")
                .modified(harmony.communityservice.board.comment.domain.ModifiedType.NOT_YET)
                .boardId(1L)
                .createdAt(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        LordCommentResponse thirdLoadCommentResponse = LordCommentResponse.builder()
                .commentId(3L)
                .comment("third comment")
                .writerName("yh")
                .userId(3L)
                .writerProfile("https://cdn.com/user3")
                .modified(harmony.communityservice.board.comment.domain.ModifiedType.NOT_YET)
                .boardId(1L)
                .createdAt(Instant.now().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .build();
        List<LordCommentResponse> loadCommentResponses = List.of(firstLoadCommentResponse,
                secondLoadCommentResponse, thirdLoadCommentResponse);
        List<Long> firstEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse firstLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(1L)
                .emojiUsers(firstEmojiUser)
                .emojiType(1L)
                .boardId(1L)
                .build();
        List<Long> secondEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse secondLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(2L)
                .emojiUsers(secondEmojiUser)
                .emojiType(2L)
                .boardId(1L)
                .build();
        List<Long> thirdEmojiUser = List.of(1L, 2L, 3L);
        LoadEmojiResponse thirdLoadEmojiResponse = LoadEmojiResponse.builder()
                .emojiId(3L)
                .emojiUsers(thirdEmojiUser)
                .emojiType(3L)
                .boardId(1L)
                .build();
        List<LoadEmojiResponse> loadEmojiResponses = List.of(firstLoadEmojiResponse, secondLoadEmojiResponse,
                thirdLoadEmojiResponse);
        List<LoadImageResponse> searchImageResponses = board.getImages().stream()
                .map(image -> new LoadImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());
        LoadBoardDetailResponse boardDetailResponse = LoadBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .loadCommentsResponse(new LoadCommentsResponse(loadCommentResponses))
                .loadEmojisResponse(new LoadEmojisResponse(loadEmojiResponses))
                .boardId(board.getBoardId().getId())
                .loadImagesResponse(new LoadImagesResponse(searchImageResponses))
                .modified(board.getType())
                .createdAt(board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .build();
        given(loadBoardPort.load(BoardId.make(1L))).willReturn(board);
        given(loadCommentsQuery.load(BoardId.make(1L))).willReturn(new LoadCommentsResponse(loadCommentResponses));
        given(loadEmojisQuery.loadByBoardId(BoardId.make(1L))).willReturn(new LoadEmojisResponse(loadEmojiResponses));

        LoadBoardDetailResponse loadBoardDetailResponse = boardQueryService.loadDetail(BoardId.make(1L));

        assertEquals(boardDetailResponse, loadBoardDetailResponse);
        then(loadBoardPort).should(times(1)).load(BoardId.make(1L));
        then(loadCommentsQuery).should(times(1)).load(BoardId.make(1L));
        then(loadEmojisQuery).should(times(1)).loadByBoardId(BoardId.make(1L));
    }
}