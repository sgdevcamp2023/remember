package harmony.communityservice.room.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.application.port.in.LoadUserStatesInRoomQuery;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadRoomInUserStatusController {

    private final LoadUserStatesInRoomQuery loadUserStatesInRoomQuery;

    @GetMapping("/search/user/status/dm/{dmId}/{userId}")
    public BaseResponse<?> searchDmRoom(@PathVariable Long userId, @PathVariable Long dmId) {
        Map<Long, ?> userStates = loadUserStatesInRoomQuery.loadUserStates(dmId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", userStates);
    }
}
