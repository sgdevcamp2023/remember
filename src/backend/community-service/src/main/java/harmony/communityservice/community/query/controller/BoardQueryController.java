package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.SearchBoardDetailResponse;
import harmony.communityservice.community.query.dto.SearchBoardResponse;
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

    @GetMapping("/search/board/list/{guildId}/{channelId}/{cursor}")
    public BaseResponse<?> searchList(@PathVariable Long guildId, @PathVariable Long channelId,
                                      @PathVariable Long cursor) {
        List<SearchBoardResponse> searchBoardResponses = boardQueryService.searchList(channelId, cursor);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchBoardResponses);
    }

    @GetMapping("/search/board/{boardId}")
    public BaseResponse<?> search(@PathVariable Long boardId) {
        SearchBoardDetailResponse searchBoardDetailResponse = boardQueryService.searchDetail(boardId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchBoardDetailResponse);
    }
}
