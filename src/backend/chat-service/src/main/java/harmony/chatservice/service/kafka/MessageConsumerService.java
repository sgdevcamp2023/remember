package harmony.chatservice.service.kafka;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "communityChatTopic", groupId = "communityGroup", containerFactory = "communityListener")
    public void consumeForCommunity(CommunityMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/guild/" + messageDto.getGuildId(), messageDto);
    }

    @KafkaListener(topics = "directChatTopic", groupId = "directGroup", containerFactory = "directListener")
    public void consumeForDirect(DirectMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/direct/" + messageDto.getRoomId(), messageDto);
    }
}