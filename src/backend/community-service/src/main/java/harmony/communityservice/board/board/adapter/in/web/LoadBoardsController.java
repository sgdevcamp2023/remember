package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.board.board.application.port.in.LoadBoardsCommand;
import harmony.communityservice.board.board.application.port.in.LoadBoardsQuery;
import harmony.communityservice.board.board.application.port.in.LoadBoardsResponse;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadBoardsController {

    private final LoadBoardsQuery loadBoardsQuery;

    @GetMapping("/search/board/list/{guildId}/{channelId}/{cursor}/{userId}")
    public BaseResponse<?> searchList(@PathVariable Long userId, @PathVariable Long guildId,
                                      @PathVariable Long channelId,
                                      @PathVariable Long cursor) {
        LoadBoardsResponse loadBoardsResponse = loadBoardsQuery.loadList(new LoadBoardsCommand(channelId, cursor));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", loadBoardsResponse);
    }
}
