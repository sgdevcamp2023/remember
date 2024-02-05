package harmony.stateservice.controller;

import harmony.stateservice.dto.ConnectionStateRequest;
import harmony.stateservice.service.CommunityServerService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommunityServerController {

    private final CommunityServerService communityServerService;

    @PostMapping("/api/state/user/connection")
    public Map<Long, String> updateSession(@RequestBody ConnectionStateRequest stateRequest) {
        return communityServerService.getUsersConnectionState(stateRequest);
    }
}