package harmony.communityservice.board.board.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.board.application.port.in.LoadBoardDetailResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardQuery;
import harmony.communityservice.board.board.application.port.in.LoadImageResponse;
import harmony.communityservice.board.board.application.port.in.LoadImagesResponse;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.board.comment.application.port.in.LoadCommentsResponse;
import harmony.communityservice.board.comment.application.port.in.LordCommentResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojiResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = LoadBoardController.class)
class LoadBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadBoardQuery loadBoardQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("게시글 단건 조회 API 테스트")
    void load_board() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
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
        List<LoadImageResponse> loadImageResponses = board.getImages().stream()
                .map(image -> new LoadImageResponse(image.getImageUrl()))
                .collect(Collectors.toList());
        LoadBoardDetailResponse boardDetailResponse = LoadBoardDetailResponse.builder()
                .title(board.getContent().getTitle())
                .content(board.getContent().getContent())
                .loadCommentsResponse(new LoadCommentsResponse(loadCommentResponses))
                .loadEmojisResponse(new LoadEmojisResponse(loadEmojiResponses))
                .boardId(board.getBoardId().getId())
                .loadImagesResponse(new LoadImagesResponse(loadImageResponses))
                .modified(board.getType())
                .createdAt(board.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")).toEpochSecond())
                .userId(board.getWriterInfo().getWriterId())
                .writerName(board.getWriterInfo().getCommonUserInfo().getNickname())
                .build();
        given(loadBoardQuery.loadDetail(BoardId.make(1L))).willReturn(boardDetailResponse);
        BaseResponse<LoadBoardDetailResponse> baseResponse = new BaseResponse<>(200, "OK", boardDetailResponse);

        mockMvc.perform(get("/api/community/search/board/{boardId}/{userId}", 1L, 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadBoardQuery).should().loadDetail(BoardId.make(1L));
    }
}