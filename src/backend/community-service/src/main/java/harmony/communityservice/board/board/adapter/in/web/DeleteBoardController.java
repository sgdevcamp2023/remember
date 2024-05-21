package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.board.board.application.port.in.DeleteBoardCommand;
import harmony.communityservice.board.board.application.port.in.DeleteBoardUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class DeleteBoardController {

    private final DeleteBoardUseCase deleteBoardUseCase;

    @DeleteMapping("/delete/board")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteBoardRequest deleteBoardRequest) {
        deleteBoardUseCase.delete(new DeleteBoardCommand(deleteBoardRequest.userId(), deleteBoardRequest.boardId()));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}


