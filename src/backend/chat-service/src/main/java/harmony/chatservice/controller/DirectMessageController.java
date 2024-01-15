package harmony.chatservice.controller;

import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageModifyRequest;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.service.DirectMessageService;
import harmony.chatservice.service.kafka.MessageProducerService;
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
    private final MessageProducerService messageProducerService;

    @MessageMapping("/direct/message")
    public void chatMessage(@Payload DirectMessageRequest messageRequest) {

        DirectMessageDto messageDto = messageService.saveDirectMessage(messageRequest);
        messageProducerService.sendMessageForDirect(messageDto);
    }

    @MessageMapping("/direct/modify")
    public void modifyMessage(@Payload DirectMessageModifyRequest modifyRequest) {

        DirectMessageDto messageDto = messageService.modifyDirectMessage(modifyRequest);
        messageProducerService.sendMessageForDirect(messageDto);
    }
}