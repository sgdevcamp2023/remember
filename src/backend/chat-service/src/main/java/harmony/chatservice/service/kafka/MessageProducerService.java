package harmony.chatservice.service.kafka;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.response.SessionDto;
import harmony.chatservice.dto.response.ConnectionEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {

    @Value("${spring.kafka.producer.community-chat-topic}")
    private String communityChatTopic;

    @Value("${spring.kafka.producer.direct-chat-topic}")
    private String directChatTopic;

    @Value("${spring.kafka.producer.emoji-chat-topic}")
    private String emojiChatTopic;

    @Value("${spring.kafka.producer.connection-event-topic}")
    private String connectionEventTopic;

    @Value("${spring.kafka.producer.session-event-topic}")
    private String sessionEventTopic;

    private final KafkaTemplate<String, CommunityMessageDto> kafkaTemplateForCommunity;

    private final KafkaTemplate<String, DirectMessageDto> kafkaTemplateForDirect;

    private final KafkaTemplate<String, EmojiDto> kafkaTemplateForEmoji;

    private final KafkaTemplate<String, ConnectionEventDto> kafkaTemplateForConnectionEvent;

    private final KafkaTemplate<String, SessionDto> kafkaTemplateForSession;

    public void sendMessageForCommunity(CommunityMessageDto messageDto) {
        log.info("messageDto {}", messageDto.getType());
        kafkaTemplateForCommunity.send(communityChatTopic, messageDto);
    }

    public void sendMessageForDirect(DirectMessageDto messageDto) {
        log.info("messageDto {}", messageDto.getType());
        kafkaTemplateForDirect.send(directChatTopic, messageDto);
    }

    public void sendMessageForEmoji(EmojiDto emojiDto) {
        log.info("emoji {}", emojiDto.getType());
        kafkaTemplateForEmoji.send(emojiChatTopic, emojiDto);
    }

    public void sendMessageForConnectionEvent(ConnectionEventDto connectionEventDto) {
        log.info("ConnectionEvent {}", connectionEventDto.getType());
        kafkaTemplateForConnectionEvent.send(connectionEventTopic, connectionEventDto);
    }

    public void sendMessageForSession(SessionDto sessionDto) {
        log.info("SessionEvent {}", sessionDto.getType());
        kafkaTemplateForSession.send(sessionEventTopic, sessionDto);
    }
}