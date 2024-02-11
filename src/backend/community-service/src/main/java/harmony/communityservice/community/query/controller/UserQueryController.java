package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.UserStatesResponseDto;
import harmony.communityservice.community.query.service.RoomQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserQueryController {

    private final UserReadQueryService userReadQueryService;
    private final RoomQueryService roomQueryService;

    @GetMapping("/guild/{guildId}/{userId}")
    public BaseResponse<?> search(@PathVariable Long guildId, @PathVariable Long userId) {
        UserStatesResponseDto userStatus = userReadQueryService.findUserStatus(guildId, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", userStatus);
    }

    @GetMapping("/dm/{dmId}/{userId}")
    public BaseResponse<?> searchDmRoom(@PathVariable Long dmId, @PathVariable Long userId) {
        Map<Long, ?> userStatus = roomQueryService.findByRoomId(dmId);
        return new BaseResponse<>(HttpStatus.OK.value(),"OK",userStatus);
    }
}
