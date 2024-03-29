package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.BoardResponseDto;
import harmony.communityservice.community.query.dto.BoardsResponseDto;
import harmony.communityservice.community.query.service.BoardQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class BoardQueryController {

    private final BoardQueryService boardQueryService;

    @GetMapping("/board/{guildId}/{channelId}/{cursor}")
    public BaseResponse<?> searchBoards(@PathVariable Long guildId, @PathVariable Long channelId,
                                        @PathVariable Long cursor) {
        List<BoardsResponseDto> boardResponseDtos = boardQueryService.findBoards(channelId, cursor);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", boardResponseDtos);
    }

    @GetMapping("/board/{boardId}")
    private BaseResponse<?> searchBoard(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardQueryService.make(boardId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", boardResponseDto);
    }
}
