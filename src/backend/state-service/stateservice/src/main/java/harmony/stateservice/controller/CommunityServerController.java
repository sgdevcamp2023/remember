package harmony.stateservice.controller;

import harmony.stateservice.dto.UserStateDto;
import harmony.stateservice.dto.request.CommunityUserStateRequest;
import harmony.stateservice.service.CommunityServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunityServerController {

    private final CommunityServerService communityServerService;

    @PostMapping("/api/state/community/user/info")
    public UserStateDto getCommunityUsersState(@RequestBody CommunityUserStateRequest stateRequest) {
        return communityServerService.getCommunityUsersState(stateRequest);
    }
}