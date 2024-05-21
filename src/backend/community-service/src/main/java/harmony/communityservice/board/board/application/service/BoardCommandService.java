package harmony.communityservice.board.board.application.service;

import harmony.communityservice.board.board.application.port.in.DeleteBoardCommand;
import harmony.communityservice.board.board.application.port.in.DeleteBoardUseCase;
import harmony.communityservice.board.board.application.port.in.DeleteChannelBoardsUseCase;
import harmony.communityservice.board.board.application.port.in.DeleteChannelsBoardsUseCase;
import harmony.communityservice.board.board.application.port.in.ModifyBoardCommand;
import harmony.communityservice.board.board.application.port.in.ModifyBoardUseCase;
import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.board.board.application.port.in.RegisterBoardUseCase;
import harmony.communityservice.board.board.application.port.out.DeleteBoardPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelBoardsPort;
import harmony.communityservice.board.board.application.port.out.DeleteChannelsBoardsPort;
import harmony.communityservice.board.board.application.port.out.ModifyBoardPort;
import harmony.communityservice.board.board.application.port.out.RegisterBoardPort;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.guild.channel.domain.Channel.ChannelId;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildReadUseCase;
import harmony.communityservice.guild.guild.domain.GuildRead;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@Transactional
@RequiredArgsConstructor
class BoardCommandService implements RegisterBoardUseCase, ModifyBoardUseCase, DeleteBoardUseCase,
        DeleteChannelBoardsUseCase, DeleteChannelsBoardsUseCase {

    private final FileConverter fileConverter;
    private final LoadGuildReadUseCase loadGuildReadUseCase;
    private final RegisterBoardPort registerBoardPort;
    private final ModifyBoardPort modifyBoardPort;
    private final DeleteBoardPort deleteBoardPort;
    private final DeleteChannelBoardsPort deleteChannelBoardsPort;
    private final DeleteChannelsBoardsPort deleteChannelsBoardsPort;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterBoardCommand registerBoardCommand, List<MultipartFile> files) {
        List<Image> images = createImages(files);
        Board board = createBoard(registerBoardCommand, images);
        registerBoardPort.register(board);
    }

    private List<Image> createImages(List<MultipartFile> images) {
        return images.stream()
                .map(fileConverter::fileToUrl)
                .map(Image::make)
                .collect(Collectors.toList());
    }

    private Board createBoard(RegisterBoardCommand registerBoardCommand, List<Image> images) {
        GuildRead guildRead = loadGuildReadUseCase.searchByUserIdAndGuildId(registerBoardCommand.userId(),
                registerBoardCommand.guildId());
        return BoardMapper.convert(registerBoardCommand, guildRead, images);
    }

    @Override
    public void modify(ModifyBoardCommand modifyBoardCommand) {
        modifyBoardPort.modify(modifyBoardCommand.userId(), BoardId.make(modifyBoardCommand.boardId()),
                modifyBoardCommand.title(), modifyBoardCommand.content());
    }

    @Override
    public void delete(DeleteBoardCommand deleteBoardCommand) {
        deleteBoardPort.delete(deleteBoardCommand.userId(), BoardId.make(deleteBoardCommand.boardId()));
        Events.send(new DeleteCommentEvent(BoardId.make(deleteBoardCommand.boardId())));
        Events.send(new DeleteEmojiEvent(BoardId.make(deleteBoardCommand.boardId())));
    }

    @Override
    public void deleteChannelBoards(Long channelId) {
        List<BoardId> boardIds = deleteChannelBoardsPort.deleteChannelBoards(ChannelId.make(channelId));
        Events.send(new DeleteCommentsEvent(boardIds));
        Events.send(new DeleteEmojisEvent(boardIds));
    }

    @Override
    public void deleteInChannels(List<ChannelId> channelIds) {
        List<BoardId> boardIds = deleteChannelsBoardsPort.deleteInChannels(channelIds);
        Events.send(new DeleteCommentsEvent(boardIds));
        Events.send(new DeleteEmojisEvent(boardIds));
    }
}
