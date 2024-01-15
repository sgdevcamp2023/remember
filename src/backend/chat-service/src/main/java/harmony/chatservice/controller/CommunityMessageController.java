package harmony.chatservice.controller;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.request.CommunityMessageRequest;
import harmony.chatservice.service.CommunityMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityMessageController {

    private final CommunityMessageService messageService;


    @MessageMapping("/guild/message")
    public void chatMessage(CommunityMessageRequest messageRequest) {
        CommunityMessageDto messageDto = messageService.saveMessage(messageRequest);
    }
}