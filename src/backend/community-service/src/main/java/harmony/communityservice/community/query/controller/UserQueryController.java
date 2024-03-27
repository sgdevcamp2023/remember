package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.SearchUserStatesInGuildResponse;
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

    @GetMapping("/search/user/status/guild/{guildId}/{userId}")
    public BaseResponse<?> searchUserStatusInGuild(@PathVariable Long guildId, @PathVariable Long userId) {
        SearchUserStatesInGuildResponse searchUserStatesInGuildResponse = userReadQueryService.searchUserStatesInGuild(
                guildId, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchUserStatesInGuildResponse);
    }

    @GetMapping("/search/user/status/dm/{dmId}/{userId}")
    public BaseResponse<?> searchDmRoom(@PathVariable Long dmId, @PathVariable Long userId) {
        Map<Long, ?> userStates = roomQueryService.searchUserStatesInRoom(dmId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", userStates);
    }
}
