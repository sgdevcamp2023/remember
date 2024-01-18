package harmony.chatservice.service;

import harmony.chatservice.domain.CommunityMessage;
import harmony.chatservice.domain.Emoji;
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
                .parentId(emojiRequest.getParentId())
                .typeId(emojiRequest.getTypeId())
                .type(emojiRequest.getType())
                .userId(emojiRequest.getUserId())
                .build();
        emoji.setEmojiId(sequenceGeneratorService.generateSequence(CommunityMessage.SEQUENCE_NAME));

        return emojiRepository.save(emoji);
    }
}