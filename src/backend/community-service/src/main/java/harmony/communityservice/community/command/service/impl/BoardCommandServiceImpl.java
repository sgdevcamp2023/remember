package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchUserReadRequest;
import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.DeleteBoardRequest;
import harmony.communityservice.community.command.dto.ModifyBoardRequest;
import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.command.repository.BoardCommandRepository;
import harmony.communityservice.community.command.service.BoardCommandService;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Image;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToBoardMapper;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
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
        targetBoard.verifyWriter(modifyBoardRequest.userId());
        targetBoard.modifyTitleAndContent(modifyBoardRequest.title(), modifyBoardRequest.content());
    }

    @Override
    public void delete(DeleteBoardRequest deleteBoardRequest) {
        Board targetBoard = boardQueryService.searchByBoardId(deleteBoardRequest.boardId());
        targetBoard.verifyWriter(deleteBoardRequest.userId());
        boardCommandRepository.delete(targetBoard);
    }
}
