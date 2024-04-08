package harmony.communityservice.room.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.dto.DeleteRoomRequest;
import harmony.communityservice.room.dto.RegisterRoomRequest;
import harmony.communityservice.room.service.command.RoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomCommandController {

    private final RoomCommandService roomCommandService;

    @PostMapping("/register/room")
    public BaseResponse<?> register(@RequestBody @Validated RegisterRoomRequest registerRoomRequest) {
        roomCommandService.register(registerRoomRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/room")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteRoomRequest deleteRoomRequest) {
        roomCommandService.delete(deleteRoomRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
