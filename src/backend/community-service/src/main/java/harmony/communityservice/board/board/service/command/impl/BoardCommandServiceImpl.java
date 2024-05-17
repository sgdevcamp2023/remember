package harmony.communityservice.board.board.service.command.impl;

import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.board.dto.DeleteBoardRequest;
import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import harmony.communityservice.board.board.mapper.ToBoardMapper;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteCommentEvent;
import harmony.communityservice.common.event.dto.inner.DeleteCommentsEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojiEvent;
import harmony.communityservice.common.event.dto.inner.DeleteEmojisEvent;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.guild.channel.adapter.out.persistence.ChannelIdJpaVO;
import harmony.communityservice.user.adapter.out.persistence.UserReadEntity;
import harmony.communityservice.user.service.command.UserReadCommandService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final FileConverter contentService;
    private final UserReadCommandService userReadCommandService;
    private final BoardCommandRepository boardCommandRepository;

    @Override
    @AuthorizeGuildMember
    public void register(RegisterBoardRequest registerBoardRequest, List<MultipartFile> files) {
        List<Image> images = createImages(files);
        Board board = createBoard(registerBoardRequest, images);
        boardCommandRepository.save(board);

    }

    private List<Image> createImages(List<MultipartFile> images) {
        return images.stream()
                .map(contentService::fileToUrl)
                .map(Image::make)
                .collect(Collectors.toList());
    }

    private Board createBoard(RegisterBoardRequest registerBoardRequest, List<Image> images) {
        UserReadEntity boardWriter = userReadCommandService.searchByUserIdAndGuildId(
                registerBoardRequest.userId(), registerBoardRequest.guildId());
        return ToBoardMapper.convert(registerBoardRequest, boardWriter, images);
    }

    @Override
    public void modify(ModifyBoardRequest modifyBoardRequest) {
        Board targetBoard = boardCommandRepository.findById(BoardId.make(modifyBoardRequest.boardId()))
                .orElseThrow(NotFoundDataException::new);
        targetBoard.modifyTitleAndContent(modifyBoardRequest);
    }

    @Override
    public void delete(DeleteBoardRequest deleteBoardRequest) {
        Board targetBoard = boardCommandRepository.findById(BoardId.make(deleteBoardRequest.boardId()))
                .orElseThrow(NotFoundDataException::new);
        targetBoard.verifyWriter(deleteBoardRequest.userId());
        boardCommandRepository.delete(targetBoard);
        Events.send(new DeleteCommentEvent(BoardId.make(deleteBoardRequest.boardId())));
        Events.send(new DeleteEmojiEvent(BoardId.make(deleteBoardRequest.boardId())));
    }

    @Override
    public void deleteAllInChannelId(Long channelId) {
        List<BoardId> boardIds = boardCommandRepository.findBoardIdsByChannelId(ChannelIdJpaVO.make(channelId));
        boardCommandRepository.deleteByChannelId(ChannelIdJpaVO.make(channelId));
        Events.send(new DeleteCommentsEvent(boardIds));
        Events.send(new DeleteEmojisEvent(boardIds));
    }

    @Override
    public void deleteAllInChannelIds(List<ChannelIdJpaVO> channelIds) {
        List<BoardId> boardIds = boardCommandRepository.findAllByChannelIds(channelIds);
        boardCommandRepository.deleteAllByChannelIds(channelIds);
        Events.send(new DeleteCommentsEvent(boardIds));
        Events.send(new DeleteEmojisEvent(boardIds));
    }
}
