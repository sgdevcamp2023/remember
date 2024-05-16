package harmony.communityservice.room.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.application.port.in.DeleteRoomCommand;
import harmony.communityservice.room.application.port.in.DeleteRoomUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomDeleteController {

    private final DeleteRoomUseCase deleteRoomUseCase;

    @DeleteMapping("/delete/room")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteRoomRequest deleteRoomRequest) {
        DeleteRoomCommand deleteRoomCommand = new DeleteRoomCommand(
                deleteRoomRequest.roomId(),
                deleteRoomRequest.firstUser(),
                deleteRoomRequest.secondUser());
        deleteRoomUseCase.delete(deleteRoomCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
