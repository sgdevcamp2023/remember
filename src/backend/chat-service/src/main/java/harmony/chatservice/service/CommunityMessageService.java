package harmony.chatservice.service;

import harmony.chatservice.domain.CommunityMessage;
import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.response.CommunityCommentResponse;
import harmony.chatservice.dto.request.CommunityMessageDeleteRequest;
import harmony.chatservice.dto.request.CommunityMessageModifyRequest;
import harmony.chatservice.dto.request.CommunityMessageRequest;
import harmony.chatservice.dto.request.EmojiDto;
import harmony.chatservice.dto.response.CommunityMessageResponse;
import harmony.chatservice.repository.CommunityMessageRepository;
import harmony.chatservice.repository.EmojiRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    public List<CommunityMessageResponse> getMessages(Long channelId) {
        List<Order> sorts = Collections.singletonList(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(0, 50, Sort.by(sorts));
        Page<CommunityMessage> messages = messageRepository.findByChannelIdAndDelCheckAndParentId(channelId, pageable)
                .orElseThrow(() -> new RuntimeException("예외 발생"));
        Page<CommunityMessageDto> messageDtos = messages.map(CommunityMessageDto::new);

        List<CommunityMessageResponse> messageResponses = new ArrayList<>();
        for (CommunityMessageDto messageDto : messageDtos) {
            List<Emoji> emojis = emojiRepository.findAllByMessageId(messageDto.getMessageId());
            List<EmojiDto> emojiDtos = emojis.stream()
                    .map(EmojiDto::new)
                    .toList();
            CommunityMessageResponse messageResponse = new CommunityMessageResponse(messageDto, emojiDtos);
            messageResponses.add(messageResponse);
        }

        return messageResponses;
    }

    public List<CommunityCommentResponse> getComments(Long parentId) {
        List<Order> sorts = Collections.singletonList(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(0, 50, Sort.by(sorts));
        Page<CommunityMessage> messages = messageRepository.findByParentIdAndDelCheckFalse(parentId, pageable);
        Page<CommunityMessageDto> messageDtos = messages.map(CommunityMessageDto::new);

        List<CommunityCommentResponse> commentResponses = new ArrayList<>();
        for (CommunityMessageDto messageDto : messageDtos) {
            List<Emoji> emojis = emojiRepository.findAllByMessageId(messageDto.getMessageId());
            List<EmojiDto> emojiDtos = emojis.stream()
                    .map(EmojiDto::new)
                    .toList();
            CommunityCommentResponse commentResponse = new CommunityCommentResponse(messageDto, emojiDtos);
            commentResponses.add(commentResponse);
        }

        return commentResponses;
    }
}