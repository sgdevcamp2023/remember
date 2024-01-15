package harmony.chatservice.service;

import harmony.chatservice.domain.DirectMessage;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageDeleteRequest;
import harmony.chatservice.dto.request.DirectMessageModifyRequest;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.repository.DirectMessageRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final DirectMessageRepository messageRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public DirectMessageDto saveDirectMessage(DirectMessageRequest messageRequest) {
        DirectMessage directMessage = DirectMessage.builder()
                .roomId(messageRequest.getRoomId())
                .parentId(messageRequest.getParentId())
                .userId(messageRequest.getUserId())
                .profileImage(messageRequest.getProfileImage())
                .type(messageRequest.getType())
                .senderName(messageRequest.getSenderName())
                .message(messageRequest.getMessage())
                .build();
        directMessage.setMessageId(sequenceGeneratorService.generateSequence(DirectMessage.SEQUENCE_NAME));
        directMessage.setCreatedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto modifyDirectMessage(DirectMessageModifyRequest modifyRequest) {

        DirectMessage directMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        directMessage.modify(modifyRequest.getMessage());
        directMessage.setModifiedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto deleteDirectMessage(DirectMessageDeleteRequest deleteRequest) {

        DirectMessage directMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        directMessage.delete(deleteRequest.getType());
        directMessage.setModifiedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }
}