package harmony.communityservice.room.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.application.port.in.RegisterRoomCommand;
import harmony.communityservice.room.application.port.in.RegisterRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomRegisterController {

    private final RegisterRoomUseCase registerRoomUseCase;

    @PostMapping("/register/room")
    public BaseResponse<?> register(@RequestBody @Validated RegisterRoomRequest registerRoomRequest) {
        RegisterRoomCommand registerRoomCommand = makeRegisterRoomCommand(registerRoomRequest);
        registerRoomUseCase.register(registerRoomCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterRoomCommand makeRegisterRoomCommand(RegisterRoomRequest registerRoomRequest) {
        return RegisterRoomCommand.builder()
                .name(registerRoomRequest.name())
                .profile(registerRoomRequest.profile())
                .members(registerRoomRequest.members())
                .userId(registerRoomRequest.userId())
                .build();
    }
}
