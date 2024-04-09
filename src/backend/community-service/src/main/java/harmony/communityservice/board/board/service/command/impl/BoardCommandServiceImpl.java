package harmony.communityservice.board.board.service.command.impl;

import harmony.communityservice.board.board.service.command.BoardCommandService;
import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.board.board.dto.DeleteBoardRequest;
import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import harmony.communityservice.board.board.repository.command.BoardCommandRepository;
import harmony.communityservice.board.domain.Image;
import harmony.communityservice.board.board.service.query.BoardQueryService;
import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.board.domain.Board;
import harmony.communityservice.user.domain.UserRead;
import harmony.communityservice.board.board.mapper.ToBoardMapper;
import harmony.communityservice.user.service.query.UserReadQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final ContentService contentService;
    private final UserReadQueryService userReadQueryService;
    private final BoardCommandRepository boardCommandRepository;
    private final BoardQueryService boardQueryService;

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
        UserRead boardWriter = userReadQueryService.searchByUserIdAndGuildId(
                new SearchUserReadRequest(registerBoardRequest.userId(), registerBoardRequest.guildId()));
        return ToBoardMapper.convert(registerBoardRequest, boardWriter, images);
    }

    @Override
    public void modify(ModifyBoardRequest modifyBoardRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(modifyBoardRequest.boardId());
        targetBoard.modifyTitleAndContent(modifyBoardRequest);
    }

    @Override
    public void delete(DeleteBoardRequest deleteBoardRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(deleteBoardRequest.boardId());
        targetBoard.verifyWriter(deleteBoardRequest.userId());
        boardCommandRepository.delete(targetBoard);
    }
}
