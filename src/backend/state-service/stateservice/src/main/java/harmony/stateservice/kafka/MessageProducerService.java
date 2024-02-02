package harmony.stateservice.kafka;

import harmony.stateservice.dto.SessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerService {


    @Value("${spring.kafka.producer.session-event-topic}")
    private String sessionChatTopic;

    private final KafkaTemplate<String, SessionDto> kafkaTemplateForSession;

    public void sendMessageForSession(SessionDto sessionDto) {
        log.info("sessionId {}", sessionDto.getSessionId());
        kafkaTemplateForSession.send(sessionChatTopic, sessionDto);
    }
}