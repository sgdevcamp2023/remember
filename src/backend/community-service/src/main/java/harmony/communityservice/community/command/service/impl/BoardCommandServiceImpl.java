package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.BoardDeleteRequestDto;
import harmony.communityservice.community.command.dto.BoardRegistrationRequestDto;
import harmony.communityservice.community.command.dto.BoardUpdateRequestDto;
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
    public void save(BoardRegistrationRequestDto requestDto, List<MultipartFile> images) {
        userReadQueryService.existsUserIdAndGuildId(requestDto.getUserId(), requestDto.getGuildId());
        List<String> imageUrls = images.stream()
                .map(contentService::imageConvertUrl).toList();
        UserRead findUserRead = userReadQueryService.findUserReadIdAndGuildId(requestDto.getUserId(),
                requestDto.getGuildId());
        Channel findChannel = channelQueryService.findChannelByChannelId(requestDto.getChannelId());
        Board board = ToBoardMapper.convert(requestDto, findUserRead, findChannel);
        boardCommandRepository.save(board);
        imageCommandService.saveImages(imageUrls, board);
    }

    @Override
    public void update(BoardUpdateRequestDto boardUpdateRequestDto) {
        Board findBoard = boardQueryService.findBoardByBoardId(boardUpdateRequestDto.getBoardId());
        findBoard.checkWriter(boardUpdateRequestDto.getUserId());
        findBoard.updateBoard(boardUpdateRequestDto.getTitle(), boardUpdateRequestDto.getContent());
    }

    @Override
    public void delete(BoardDeleteRequestDto requestDto) {
        Board findBoard = boardQueryService.findBoardByBoardId(requestDto.getBoardId());
        findBoard.checkWriter(requestDto.getUserId());
        boardCommandRepository.delete(findBoard);
    }
}
