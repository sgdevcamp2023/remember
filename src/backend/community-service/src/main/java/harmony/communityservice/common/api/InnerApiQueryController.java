package harmony.communityservice.common.api;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.dto.LoadRoomsAndGuildsResponse;
import harmony.communityservice.common.service.LoadUserBelongsQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class InnerApiQueryController {

    private final LoadUserBelongsQuery loadUserBelongsQuery;

    @GetMapping("/search/rooms/guilds/{userId}")
    public BaseResponse<?> searchRoomsAndGuildsBelongToUser(@PathVariable Long userId) {
        LoadRoomsAndGuildsResponse loadRoomsAndGuildsResponse = loadUserBelongsQuery.load(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", loadRoomsAndGuildsResponse);
    }
}
