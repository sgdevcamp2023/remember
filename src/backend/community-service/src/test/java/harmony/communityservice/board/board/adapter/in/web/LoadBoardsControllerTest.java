package harmony.communityservice.board.board.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.board.board.application.port.in.LoadBoardResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardsCommand;
import harmony.communityservice.board.board.application.port.in.LoadBoardsQuery;
import harmony.communityservice.board.board.application.port.in.LoadBoardsResponse;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.domain.ModifiedType;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojiResponse;
import harmony.communityservice.board.emoji.application.port.in.LoadEmojisResponse;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.utils.ErrorLogPrinter;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LoadBoardsController.class)
class LoadBoardsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoadBoardsQuery loadBoardsQuery;

    @MockBean
    private ErrorLogPrinter errorLogPrinter;

    @Test
    @DisplayName("게시글 리스트 조회 API 테스트")
    void load_boards() throws Exception {
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
        LoadBoardsResponse loadBoardsResponse = new LoadBoardsResponse(loadBoardResponses);
        ObjectMapper objectMapper = new ObjectMapper();
        BaseResponse<LoadBoardsResponse> baseResponse = new BaseResponse<>(200, "OK", loadBoardsResponse);
        given(loadBoardsQuery.loadList(new LoadBoardsCommand(1L, 45L))).willReturn(loadBoardsResponse);

        mockMvc.perform(get("/api/community/search/board/list/{guildId}/{channelId}/{cursor}/{userId}",
                        1, 1, 45, 1))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(baseResponse)));

        then(loadBoardsQuery).should().loadList(new LoadBoardsCommand(1L, 45L));
    }
}