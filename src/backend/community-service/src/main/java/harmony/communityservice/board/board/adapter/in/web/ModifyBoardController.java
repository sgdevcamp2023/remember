package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.board.board.application.port.in.ModifyBoardCommand;
import harmony.communityservice.board.board.application.port.in.ModifyBoardUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyBoardController {

    private final ModifyBoardUseCase modifyBoardUseCase;

    @PatchMapping("/update/board")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyBoardRequest modifyBoardRequest) {
        ModifyBoardCommand modifyBoardCommand = getModifyBoardCommand(modifyBoardRequest);
        modifyBoardUseCase.modify(modifyBoardCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private ModifyBoardCommand getModifyBoardCommand(ModifyBoardRequest modifyBoardRequest) {
        return ModifyBoardCommand.builder()
                .boardId(modifyBoardRequest.boardId())
                .title(modifyBoardRequest.title())
                .userId(modifyBoardRequest.userId())
                .content(modifyBoardRequest.content())
                .build();
    }
}
