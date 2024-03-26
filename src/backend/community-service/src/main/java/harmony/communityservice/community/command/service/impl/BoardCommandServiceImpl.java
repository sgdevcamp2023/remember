package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.DeleteBoardRequest;
import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.command.dto.ModifyBoardRequest;
import harmony.communityservice.community.command.repository.BoardCommandRepository;
import harmony.communityservice.community.command.service.BoardCommandService;
import harmony.communityservice.community.command.service.ImageCommandService;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.domain.UserRead;
import harmony.communityservice.community.mapper.ToBoardMapper;
import harmony.communityservice.community.query.service.BoardQueryService;
import harmony.communityservice.community.query.service.ChannelQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {

    private final ContentService contentService;
    private final UserReadQueryService userReadQueryService;
    private final ChannelQueryService channelQueryService;
    private final ImageCommandService imageCommandService;
    private final BoardCommandRepository boardCommandRepository;
    private final BoardQueryService boardQueryService;

    @Override
    public void register(RegisterBoardRequest registerBoardRequest, List<MultipartFile> images) {
        userReadQueryService.existsByUserIdAndGuildId(registerBoardRequest.getUserId(), registerBoardRequest.getGuildId());
        List<String> imageUrls = images.stream()
                .map(contentService::convertFileToUrl).toList();
        UserRead findUserRead = userReadQueryService.searchByUserIdAndGuildId(registerBoardRequest.getUserId(),
                registerBoardRequest.getGuildId());
        Channel findChannel = channelQueryService.searchByChannelId(registerBoardRequest.getChannelId());
        Board board = ToBoardMapper.convert(registerBoardRequest, findUserRead, findChannel);
        boardCommandRepository.save(board);
        imageCommandService.registerImagesInBoard(imageUrls, board);
    }

    @Override
    public void modify(ModifyBoardRequest modifyBoardRequest) {
        Board findBoard = boardQueryService.searchByBoardId(modifyBoardRequest.getBoardId());
        findBoard.verifyWriter(modifyBoardRequest.getUserId());
        findBoard.modifyTitleAndContent(modifyBoardRequest.getTitle(), modifyBoardRequest.getContent());
    }

    @Override
    public void delete(DeleteBoardRequest deleteBoardRequest) {
        Board findBoard = boardQueryService.searchByBoardId(deleteBoardRequest.getBoardId());
        findBoard.verifyWriter(deleteBoardRequest.getUserId());
        boardCommandRepository.delete(findBoard);
    }
}
