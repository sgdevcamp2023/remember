package harmony.chatservice.controller;

import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.service.DirectMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService messageService;

    @MessageMapping("/direct/message")
    public void chatMessage(@Payload DirectMessageRequest messageRequest) {

        DirectMessageDto messageDto = messageService.saveDirectMessage(messageRequest);
    }
}