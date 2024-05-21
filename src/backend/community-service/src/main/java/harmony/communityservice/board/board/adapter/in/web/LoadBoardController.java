package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.board.board.application.port.in.LoadBoardDetailResponse;
import harmony.communityservice.board.board.application.port.in.LoadBoardQuery;
import harmony.communityservice.board.board.domain.Board.BoardId;
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
public class LoadBoardController {

    private final LoadBoardQuery loadBoardQuery;

    @GetMapping("/search/board/{boardId}/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId, @PathVariable Long boardId) {
        LoadBoardDetailResponse loadBoardDetailResponse = loadBoardQuery.loadDetail(BoardId.make(boardId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", loadBoardDetailResponse);
    }
}
