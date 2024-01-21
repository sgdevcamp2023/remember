package harmony.chatservice.service;

import harmony.chatservice.domain.DirectMessage;
import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageDeleteRequest;
import harmony.chatservice.dto.request.DirectMessageModifyRequest;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.repository.DirectMessageRepository;
import harmony.chatservice.repository.EmojiRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DirectMessageService {

    private final EmojiRepository emojiRepository;
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
                .files(null)
                .build();
        directMessage.setMessageId(sequenceGeneratorService.generateSequence(DirectMessage.SEQUENCE_NAME));
        directMessage.setCreatedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto modifyDirectMessage(DirectMessageModifyRequest modifyRequest) {

        DirectMessage directMessage = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        directMessage.modify(modifyRequest.getMessage(), modifyRequest.getType());
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

    @Transactional
    public DirectMessageDto saveMessageWithFile(DirectMessageRequest messageRequest, List<String> uploadFiles) {
        DirectMessage directMessage = DirectMessage.builder()
                .roomId(messageRequest.getRoomId())
                .parentId(messageRequest.getParentId())
                .userId(messageRequest.getUserId())
                .profileImage(messageRequest.getProfileImage())
                .type(messageRequest.getType())
                .senderName(messageRequest.getSenderName())
                .message(messageRequest.getMessage())
                .files(uploadFiles)
                .build();
        directMessage.setMessageId(sequenceGeneratorService.generateSequence(DirectMessage.SEQUENCE_NAME));
        directMessage.setCreatedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }

    public Page<DirectMessageDto> getDirectMessages(Long roomId) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("message", roomId);
        for (DirectMessageDto messageDto : messageDtos) {
            List<EmojiDto> emojiDtos = emojisToEmojiDtos(messageDto.getMessageId());
            messageDto.setEmojis(emojiDtos);
            messageDto.setCount(messageRepository.countByParentId(messageDto.getMessageId()));
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> getComments(Long parentId) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("comment", parentId);
        for (DirectMessageDto messageDto : messageDtos) {
            List<EmojiDto> emojiDtos = emojisToEmojiDtos(messageDto.getMessageId());
            messageDto.setEmojis(emojiDtos);
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> messagesToMessageDtos(String type, Long id) {
        List<Order> sorts = Collections.singletonList(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(0, 50, Sort.by(sorts));
        Page<DirectMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByRoomIdAndDelCheckAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndDelCheckFalse(id, pageable);
        }

        return (messages != null) ? messages.map(DirectMessageDto::new) : Page.empty(pageable);
    }

    public List<EmojiDto> emojisToEmojiDtos(Long messageId) {
        List<Emoji> emojis = emojiRepository.findAllByDirectMessageId(messageId);
        return emojis.stream()
                .map(EmojiDto::new)
                .toList();
    }
}