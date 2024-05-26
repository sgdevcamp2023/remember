package harmony.communityservice.board.board.application.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;

import harmony.communityservice.board.board.application.port.in.DeleteBoardCommand;
import harmony.communityservice.board.board.application.port.in.ModifyBoardCommand;
import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.board.board.application.port.out.DeleteBoardPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelBoardsPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelsBoardsPort;
import harmony.communityservice.board.board.application.port.out.ModifyBoardPort;
import harmony.communityservice.board.board.application.port.out.RegisterBoardPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadUseCase;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.domain.GuildRead.GuildReadId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class BoardCommandServiceTest {

    @Mock
    FileConverter fileConverter;
    @Mock
    LoadGuildReadUseCase loadGuildReadUseCase;
    @Mock
    RegisterBoardPort registerBoardPort;
    @Mock
    ModifyBoardPort modifyBoardPort;
    @Mock
    DeleteBoardPort deleteBoardPort;
    @Mock
    DeleteChannelBoardsPort deleteChannelBoardsPort;
    @Mock
    DeleteChannelsBoardsPort deleteChannelsBoardsPort;
    @Mock
    ApplicationEventPublisher publisher;
    BoardCommandService boardCommandService;

    @BeforeEach
    void setting() {
        boardCommandService = new BoardCommandService(fileConverter, loadGuildReadUseCase, registerBoardPort,
                modifyBoardPort, deleteBoardPort, deleteChannelBoardsPort, deleteChannelsBoardsPort);
        Events.register(publisher);
    }

    @Test
    @DisplayName("게시글 등록 테스트")
    void register_board() {
        assertNotNull(boardCommandService);

        MultipartFile firstFile = new MockMultipartFile("file", "test", "text/plain", "first content".getBytes());
        MultipartFile secondFile = new MockMultipartFile("file", "test", "text/plain", "second content".getBytes());
        MultipartFile thirdFile = new MockMultipartFile("file", "test", "text/plain", "third content".getBytes());
        List<MultipartFile> multipartFiles = List.of(firstFile, secondFile, thirdFile);
        Image firstImage = Image.make("https://cdn.com/first");
        Image secondImage = Image.make("https://cdn.com/second");
        Image thirdImage = Image.make("https://cdn.com/third");
        List<Image> images = List.of(firstImage, secondImage, thirdImage);

        GuildRead guildRead = GuildRead.builder()
                .userProfile("http://user.com/test1")
                .userNickname("0chord")
                .guildId(GuildId.make(1L))
                .guildReadId(GuildReadId.make(1L))
                .profile("http://guild.com/test1")
                .name("first_guild")
                .userId(UserId.make(1L))
                .build();

        Board board = Board.builder()
                .boardId(BoardId.make(Threshold.MIN.getValue()))
                .title("first_board")
                .content("first_content")
                .channelId(ChannelId.make(1L))
                .writerId(guildRead.getUserId().getId())
                .username(guildRead.getCommonUserInfo().getNickname())
                .profile(guildRead.getCommonUserInfo().getProfile())
                .images(images)
                .build();

        RegisterBoardCommand registerBoardCommand = RegisterBoardCommand.builder()
                .channelId(1L)
                .content("first_content")
                .userId(1L)
                .title("first_board")
                .guildId(1L)
                .build();
        given(fileConverter.fileToUrl(firstFile)).willReturn("https://cdn.com/first");
        given(fileConverter.fileToUrl(secondFile)).willReturn("https://cdn.com/second");
        given(fileConverter.fileToUrl(thirdFile)).willReturn("https://cdn.com/third");
        given(loadGuildReadUseCase.loadByUserIdAndGuildId(1L, 1L)).willReturn(guildRead);
        willDoNothing().given(registerBoardPort).register(board);

        boardCommandService.register(registerBoardCommand, multipartFiles);

        then(fileConverter).should(times(1)).fileToUrl(firstFile);
        then(fileConverter).should(times(1)).fileToUrl(secondFile);
        then(fileConverter).should(times(1)).fileToUrl(thirdFile);
        then(loadGuildReadUseCase).should(times(1)).loadByUserIdAndGuildId(1L, 1L);
        then(registerBoardPort).should(times(1)).register(board);
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void modify_board() {
        assertNotNull(boardCommandService);

        ModifyBoardCommand modifyBoardCommand = ModifyBoardCommand.builder()
                .content("modify_content")
                .title("modify_board")
                .boardId(1L)
                .userId(1L)
                .build();
        willDoNothing().given(modifyBoardPort).modify(1L, BoardId.make(1L), "modify_board", "modify_content");

        boardCommandService.modify(modifyBoardCommand);

        then(modifyBoardPort).should(times(1)).modify(1L, BoardId.make(1L), "modify_board", "modify_content");
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void delete_board() {
        assertNotNull(boardCommandService);

        willDoNothing().given(deleteBoardPort).delete(1L, BoardId.make(1L));

        boardCommandService.delete(new DeleteBoardCommand(1L, 1L));

        then(deleteBoardPort).should(times(1)).delete(1L, BoardId.make(1L));
        then(publisher).should(times(1)).publishEvent(new DeleteCommentEvent(BoardId.make(1L)));
        then(publisher).should(times(1)).publishEvent(new DeleteEmojiEvent(BoardId.make(1L)));
    }

    @Test
    @DisplayName("채널 내의 게시글 삭제 테스트")
    void delete_channel_boards() {
        List<BoardId> boardIds = List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L));
        given(deleteChannelBoardsPort.deleteChannelBoards(ChannelId.make(1L))).willReturn(boardIds);

        boardCommandService.deleteChannelBoards(1L);

        then(deleteChannelBoardsPort).should(times(1)).deleteChannelBoards(ChannelId.make(1L));
        then(publisher).should(times(1)).publishEvent(new DeleteCommentsEvent(boardIds));
        then(publisher).should(times(1)).publishEvent(new DeleteEmojisEvent(boardIds));
    }

    @Test
    @DisplayName("길드 내의 게시글 삭제 테스트")
    void delete_guild_boards() {
        List<BoardId> boardIds = List.of(BoardId.make(1L), BoardId.make(2L), BoardId.make(3L),BoardId.make(4L));
        List<ChannelId> channelIds = List.of(ChannelId.make(1L), ChannelId.make(2L), ChannelId.make(3L),
                ChannelId.make(4L));
        given(deleteChannelsBoardsPort.deleteInChannels(channelIds)).willReturn(boardIds);

        boardCommandService.deleteInChannels(channelIds);

        then(deleteChannelsBoardsPort).should(times(1)).deleteInChannels(channelIds);
        then(publisher).should(times(1)).publishEvent(new DeleteCommentsEvent(boardIds));
        then(publisher).should(times(1)).publishEvent(new DeleteEmojisEvent(boardIds));
    }
}