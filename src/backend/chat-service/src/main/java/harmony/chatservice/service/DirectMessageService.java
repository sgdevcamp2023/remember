package harmony.chatservice.service;

import harmony.chatservice.domain.DirectMessage;
import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.request.DirectMessageDeleteRequest;
import harmony.chatservice.dto.request.DirectMessageModifyRequest;
import harmony.chatservice.dto.request.DirectMessageRequest;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.exception.DataNotFoundException;
import harmony.chatservice.exception.ExceptionStatus;
import harmony.chatservice.repository.DirectMessageRepository;
import harmony.chatservice.repository.EmojiRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                .orElseThrow(() -> new DataNotFoundException(ExceptionStatus.DATA_NOT_FOUND));

        directMessage.modify(modifyRequest.getMessage(), modifyRequest.getType());
        directMessage.setModifiedAt(LocalDateTime.now());

        return new DirectMessageDto(messageRepository.save(directMessage));
    }

    @Transactional
    public DirectMessageDto deleteDirectMessage(DirectMessageDeleteRequest deleteRequest) {

        DirectMessage directMessage = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new DataNotFoundException(ExceptionStatus.DATA_NOT_FOUND));

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

    public Page<DirectMessageDto> getDirectMessages(Long roomId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("message", roomId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        Map<Long, Long> messageCounts = getMessageCounts(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
            messageDto.setCount(messageCounts.getOrDefault(messageDto.getMessageId(), 0L));
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> getComments(Long parentId, int page, int size) {
        Page<DirectMessageDto> messageDtos = messagesToMessageDtos("comment", parentId, page, size);
        List<Long> messageIds = getMessageIds(messageDtos);

        Map<Long, List<EmojiDto>> emojiMap = getEmojisForDirectMessages(messageIds);
        for (DirectMessageDto messageDto : messageDtos) {
            messageDto.setEmojis(emojiMap.getOrDefault(messageDto.getMessageId(), Collections.emptyList()));
        }

        return messageDtos;
    }

    public Page<DirectMessageDto> messagesToMessageDtos(String type, Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<DirectMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByRoomIdAndDelCheckAndParentId(id, pageable);
        } else if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndDelCheckFalse(id, pageable);
        }

        return (messages != null) ? messages.map(DirectMessageDto::new) : Page.empty(pageable);
    }

    public Map<Long, List<EmojiDto>> getEmojisForDirectMessages(List<Long> messageIds) {
        List<Emoji> emojis = emojiRepository.findEmojisByDirectMessageIds(messageIds);
        Map<Long, List<EmojiDto>> emojiMap = new HashMap<>();

        for (Long messageId : messageIds) {
            List<EmojiDto> emojiDtos = new ArrayList<>();
            for (Emoji emoji : emojis) {
                if (messageId.equals(emoji.getDirectMessageId())) {
                    EmojiDto emojiDto = new EmojiDto(emoji);
                    emojiDtos.add(emojiDto);
                }
            }
            emojiMap.put(messageId, emojiDtos);
        }
        return emojiMap;
    }

    public Map<Long, Long> getMessageCounts(List<Long> messageIds) {
        List<DirectMessage> messages = messageRepository.countMessagesByParentIds(messageIds);
        Map<Long, Long> messageCounts = new HashMap<>();

        for (Long messageId : messageIds) {
            long count = 0L;
            for (DirectMessage message : messages) {
                if (message.getParentId().equals(messageId)) {
                    count += 1;
                }
            }
            messageCounts.put(messageId, count);
        }

        return messageCounts;
    }

    public List<Long> getMessageIds(Page<DirectMessageDto> messageDtos) {
        return messageDtos.getContent().stream()
                .map(DirectMessageDto::getMessageId)
                .collect(Collectors.toList());
    }
}