package harmony.chatservice.controller;

import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageDeleteRequest;
import harmony.chatservice.dto.request.DirectMessageModifyRequest;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.service.DirectMessageService;
import harmony.chatservice.service.FileUploadService;
import harmony.chatservice.kafka.MessageProducerService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DirectMessageController {

    private final DirectMessageService messageService;
    private final FileUploadService fileUploadService;
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

    @MessageMapping("/direct/delete")
    public void deleteMessage(@Payload DirectMessageDeleteRequest deleteRequest) {

        DirectMessageDto messageDto = messageService.deleteDirectMessage(deleteRequest);
        messageProducerService.sendMessageForDirect(messageDto);
    }

    @MessageMapping("/direct/typing")
    public void typingMessage(@Payload DirectMessageDto messageDto) {

        messageProducerService.sendMessageForDirect(messageDto);
    }

    @GetMapping("/api/direct/messages/room/{roomId}")
    public Page<DirectMessageDto> getMessages(@PathVariable("roomId") Long roomId) {

        return messageService.getDirectMessages(roomId);
    }

    @GetMapping("/api/direct/comments/{parentId}")
    public Page<DirectMessageDto> getComments(@PathVariable("parentId") Long parentId) {

        return messageService.getComments(parentId);
    }

    @PostMapping("/api/direct/message/file")
    public void uploadFile(@RequestPart DirectMessageRequest directMessageRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        List<String> uploadFiles = fileUploadService.uploadFile(files);
        DirectMessageDto messageDto = messageService.saveMessageWithFile(directMessageRequest, uploadFiles);
        messageProducerService.sendMessageForDirect(messageDto);
    }
}