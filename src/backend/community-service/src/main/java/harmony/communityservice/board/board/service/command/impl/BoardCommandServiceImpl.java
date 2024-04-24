package harmony.communityservice.board.board.service.command.impl;

import harmony.communityservice.board.board.dto.DeleteBoardRequest;
import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import harmony.communityservice.board.board.mapper.ToBoardMapper;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.board.comment.service.command.CommentCommandService;
import harmony.communityservice.board.board.domain.Board;
import harmony.communityservice.board.board.domain.Image;
import harmony.communityservice.board.emoji.service.command.EmojiCommandService;
import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.user.service.command.UserReadCommandService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final ContentService contentService;
    private final UserReadCommandService userReadCommandService;
    private final CommentCommandService commentCommandService;
    private final EmojiCommandService emojiCommandService;
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
                .map(contentService::convertFileToUrl)
                .map(Image::make)
                .collect(Collectors.toList());
    }

    private Board createBoard(RegisterBoardRequest registerBoardRequest, List<Image> images) {
        UserRead boardWriter = userReadCommandService.searchByUserIdAndGuildId(
                registerBoardRequest.userId(), registerBoardRequest.guildId());
        return ToBoardMapper.convert(registerBoardRequest, boardWriter, images);
    }

    @Override
    public void modify(ModifyBoardRequest modifyBoardRequest) {
        Board targetBoard = boardCommandRepository.findById(modifyBoardRequest.boardId())
                .orElseThrow(NotFoundDataException::new);
        targetBoard.modifyTitleAndContent(modifyBoardRequest);
    }

    @Override
    public void delete(DeleteBoardRequest deleteBoardRequest) {
        Board targetBoard = boardCommandRepository.findById(deleteBoardRequest.boardId())
                .orElseThrow(NotFoundDataException::new);
        targetBoard.verifyWriter(deleteBoardRequest.userId());
        boardCommandRepository.delete(targetBoard);
        commentCommandService.deleteListByBoardId(deleteBoardRequest.boardId());
        emojiCommandService.deleteListByBoardId(deleteBoardRequest.boardId());
    }
}
