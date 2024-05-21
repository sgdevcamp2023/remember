package harmony.communityservice.board.board.adapter.in.web;

import harmony.communityservice.board.board.application.port.in.RegisterBoardCommand;
import harmony.communityservice.board.board.application.port.in.RegisterBoardUseCase;
import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RegisterBoardController {

    private final RegisterBoardUseCase registerBoardUseCase;

    @PostMapping("/register/board")
    public BaseResponse<?> register(
            @RequestPart(value = "registerBoardRequest") RegisterBoardRequest registerBoardRequest,
            @RequestPart(name = "images", required = false) List<MultipartFile> images) {
        RegisterBoardCommand registerBoardCommand = getRegisterBoardCommand(registerBoardRequest);
        registerBoardUseCase.register(registerBoardCommand, images);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterBoardCommand getRegisterBoardCommand(RegisterBoardRequest registerBoardRequest) {
        return RegisterBoardCommand.builder()
                .title(registerBoardRequest.title())
                .guildId(registerBoardRequest.guildId())
                .userId(registerBoardRequest.userId())
                .content(registerBoardRequest.content())
                .channelId(registerBoardRequest.channelId())
                .build();
    }
}
