package harmony.chatservice.service;

import harmony.chatservice.domain.CommunityMessage;
import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.request.CommunityMessageDeleteRequest;
import harmony.chatservice.dto.request.CommunityMessageModifyRequest;
import harmony.chatservice.dto.request.CommunityMessageRequest;
import harmony.chatservice.dto.request.EmojiDto;
import harmony.chatservice.repository.CommunityMessageRepository;
import harmony.chatservice.repository.EmojiRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityMessageService {

    private final EmojiRepository emojiRepository;
    private final CommunityMessageRepository messageRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public CommunityMessageDto saveMessage(CommunityMessageRequest messageRequest) {
        CommunityMessage communityMessage = CommunityMessage.builder()
                .guildId(messageRequest.getGuildId())
                .channelId(messageRequest.getChannelId())
                .userId(messageRequest.getUserId())
                .parentId(messageRequest.getParentId())
                .profileImage(messageRequest.getProfileImage())
                .type(messageRequest.getType())
                .senderName(messageRequest.getSenderName())
                .message(messageRequest.getMessage())
                .delCheck(false)
                .files(null)
                .build();

        communityMessage.setMessageId(sequenceGeneratorService.generateSequence(CommunityMessage.SEQUENCE_NAME));
        communityMessage.setCreatedAt(LocalDateTime.now());

        return new CommunityMessageDto(messageRepository.save(communityMessage));
    }

    @Transactional
    public CommunityMessageDto modifyMessage(CommunityMessageModifyRequest modifyRequest) {
        CommunityMessage message = messageRepository.findById(modifyRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        message.modify(modifyRequest.getMessage(), modifyRequest.getType());
        message.setModifiedAt(LocalDateTime.now());

        return new CommunityMessageDto(messageRepository.save(message));
    }

    @Transactional
    public CommunityMessageDto deleteMessage(CommunityMessageDeleteRequest deleteRequest) {
        CommunityMessage message = messageRepository.findById(deleteRequest.getMessageId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));

        message.delete(deleteRequest.getType());
        message.setModifiedAt(LocalDateTime.now());

        return new CommunityMessageDto(messageRepository.save(message));
    }

    @Transactional
    public CommunityMessageDto saveMessageWithFile(CommunityMessageRequest messageRequest, List<String> uploadFiles) {
        CommunityMessage communityMessage = CommunityMessage.builder()
                .guildId(messageRequest.getGuildId())
                .channelId(messageRequest.getChannelId())
                .userId(messageRequest.getUserId())
                .parentId(messageRequest.getParentId())
                .profileImage(messageRequest.getProfileImage())
                .type(messageRequest.getType())
                .senderName(messageRequest.getSenderName())
                .message(messageRequest.getMessage())
                .delCheck(false)
                .files(uploadFiles)
                .build();

        communityMessage.setMessageId(sequenceGeneratorService.generateSequence(CommunityMessage.SEQUENCE_NAME));
        communityMessage.setCreatedAt(LocalDateTime.now());

        return new CommunityMessageDto(messageRepository.save(communityMessage));
    }

    public Page<CommunityMessageDto> getMessages(Long channelId) {
        Page<CommunityMessageDto> messageDtos = messagesToMessageDtos("message", channelId);
        for (CommunityMessageDto messageDto : messageDtos) {
            List<EmojiDto> emojiDtos = emojisToEmojiDtos(messageDto.getMessageId());
            messageDto.setEmojis(emojiDtos);
            messageDto.setCount(messageRepository.countByParentId(messageDto.getMessageId()));
        }

        return messageDtos;
    }

    public Page<CommunityMessageDto> getComments(Long parentId) {
        Page<CommunityMessageDto> messageDtos = messagesToMessageDtos("comment", parentId);
        for (CommunityMessageDto messageDto : messageDtos) {
            List<EmojiDto> emojiDtos = emojisToEmojiDtos(messageDto.getMessageId());
            messageDto.setEmojis(emojiDtos);
        }

        return messageDtos;
    }

    public Page<CommunityMessageDto> messagesToMessageDtos(String type, Long id) {
        List<Order> sorts = Collections.singletonList(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(0, 50, Sort.by(sorts));
        Page<CommunityMessage> messages = null;
        if (type.equals("message")) {
            messages = messageRepository.findByChannelIdAndDelCheckAndParentId(id, pageable)
                    .orElseThrow(() -> new RuntimeException("예외 발생"));
        }
        if (type.equals("comment")) {
            messages = messageRepository.findByParentIdAndDelCheckFalse(id, pageable)
                    .orElseThrow(() -> new RuntimeException("예외 발생"));
        }

        return Objects.requireNonNull(messages, "예외 발생").map(CommunityMessageDto::new);
    }

    public List<EmojiDto> emojisToEmojiDtos(Long messageId) {
        List<Emoji> emojis = emojiRepository.findAllByMessageId(messageId);
        return emojis.stream()
                .map(EmojiDto::new)
                .toList();
    }
}