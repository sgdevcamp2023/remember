package harmony.stateservice.controller;

import harmony.stateservice.dto.SessionDto;
import harmony.stateservice.service.ChatServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatServerController {

    private final ChatServerService chatServerService;

    @PostMapping("/api/state/update/session")
    public String updateSession(@RequestBody SessionDto sessionDto) {
        return chatServerService.updateSession(sessionDto);
    }
}