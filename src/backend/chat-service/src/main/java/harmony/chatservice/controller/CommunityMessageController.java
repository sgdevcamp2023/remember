package harmony.chatservice.controller;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.request.CommunityMessageDeleteRequest;
import harmony.chatservice.dto.request.CommunityMessageModifyRequest;
import harmony.chatservice.dto.request.CommunityMessageRequest;
import harmony.chatservice.service.CommunityMessageService;
import harmony.chatservice.service.FileUploadService;
import harmony.chatservice.kafka.MessageProducerService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityMessageController {

    private final CommunityMessageService messageService;
    private final FileUploadService fileUploadService;
    private final MessageProducerService messageProducerService;

    @MessageMapping("/guild/message")
    public void chatMessage(CommunityMessageRequest messageRequest) {
        CommunityMessageDto messageDto = messageService.saveMessage(messageRequest);
        messageProducerService.sendMessageForCommunity(messageDto);
    }

    @MessageMapping("/guild/modify")
    public void modifyMessage(CommunityMessageModifyRequest modifyRequest) {
        CommunityMessageDto messageDto = messageService.modifyMessage(modifyRequest);
        messageProducerService.sendMessageForCommunity(messageDto);
    }

    @MessageMapping("/guild/delete")
    public void deleteMessage(CommunityMessageDeleteRequest deleteRequest) {
        CommunityMessageDto messageDto = messageService.deleteMessage(deleteRequest);
        messageProducerService.sendMessageForCommunity(messageDto);
    }

    @MessageMapping("/guild/typing")
    public void typingMessage(CommunityMessageDto messageDto) {

        messageProducerService.sendMessageForCommunity(messageDto);
    }

    @GetMapping("/api/chat-service/community/messages/channel")
    public Page<CommunityMessageDto> getMessages(@RequestParam(value = "channelId") Long channelId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "30") int size) {

        return messageService.getMessages(channelId, page, size);
    }

    @GetMapping("/api/chat-service/community/comments")
    public Page<CommunityMessageDto> getComments(@RequestParam(value = "parentId") Long parentId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "30") int size) {

        return messageService.getComments(parentId, page, size);
    }

    @PostMapping("/api/chat-service/community/message/file")
    public void uploadFile(@RequestPart CommunityMessageRequest communityMessageRequest,
                           @RequestPart(value = "files", required = false) List<MultipartFile> files) throws IOException {

        List<String> uploadFiles = fileUploadService.uploadFile(files);
        CommunityMessageDto messageDto = messageService.saveMessageWithFile(communityMessageRequest, uploadFiles);
        messageProducerService.sendMessageForCommunity(messageDto);
    }
}