package harmony.stateservice.kafka;

import harmony.stateservice.dto.SessionDto;
import harmony.stateservice.service.StateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final StateService stateService;

    @KafkaListener(topics = "sessionChatTopic", groupId = "sessionGroup", containerFactory = "sessionListener")
    public void consumeForSession(SessionDto sessionDto){
        stateService.saveSessionInfo(sessionDto);
        log.info("sessionId {}", sessionDto.getSessionId());
        log.info("userId {}", sessionDto.getUserId());
    }
}