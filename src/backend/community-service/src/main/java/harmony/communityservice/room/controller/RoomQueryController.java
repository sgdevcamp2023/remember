package harmony.communityservice.room.controller;

import harmony.communityservice.common.annotation.AuthorizeUser;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.dto.SearchRoomsResponse;
import harmony.communityservice.room.service.query.RoomQueryService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthorizeUser
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomQueryController {

    private final RoomQueryService roomQueryService;

    @GetMapping("/search/rooms/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId) {
        SearchRoomsResponse searchRoomsResponse = roomQueryService.searchList(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchRoomsResponse);
    }

    @GetMapping("/search/user/status/dm/{dmId}/{userId}")
    public BaseResponse<?> searchDmRoom(@PathVariable Long userId, @PathVariable Long dmId) {
        Map<Long, ?> userStates = roomQueryService.searchUserStatesInRoom(dmId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", userStates);
    }
}
