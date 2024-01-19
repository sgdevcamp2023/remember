package harmony.chatservice.service;

import harmony.chatservice.domain.Emoji;
import harmony.chatservice.dto.request.EmojiDeleteRequest;
import harmony.chatservice.dto.request.EmojiDto;
import harmony.chatservice.dto.request.EmojiRequest;
import harmony.chatservice.repository.EmojiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmojiService {

    private final EmojiRepository emojiRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Transactional
    public Emoji saveEmoji(EmojiRequest emojiRequest) {
        Emoji emoji = Emoji.builder()
                .guildId(emojiRequest.getGuildId())
                .channelId(emojiRequest.getChannelId())
                .roomId(emojiRequest.getRoomId())
                .messageId(emojiRequest.getMessageId())
                .typeId(emojiRequest.getTypeId())
                .type(emojiRequest.getType())
                .userId(emojiRequest.getUserId())
                .build();
        emoji.setEmojiId(sequenceGeneratorService.generateSequence(Emoji.SEQUENCE_NAME));

        return emojiRepository.save(emoji);
    }

    @Transactional
    public EmojiDto deleteEmoji(EmojiDeleteRequest deleteRequest) {
        Emoji emoji = emojiRepository.findById(deleteRequest.getEmojiId())
                .orElseThrow(() -> new RuntimeException("예외 발생"));
        EmojiDto emojiDto = new EmojiDto(emoji);
        emojiDto.setType(deleteRequest.getType());

        emojiRepository.delete(emoji);

        return emojiDto;
    }
}