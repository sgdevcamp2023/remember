package harmony.chatservice.service.kafka;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProducerService {

    @Value("${spring.kafka.producer.community-chat-topic}")
    private String communityChatTopic;

    @Value("${spring.kafka.producer.direct-chat-topic}")
    private String directChatTopic;

    private final KafkaTemplate<String, CommunityMessageDto> kafkaTemplateForCommunity;

    private final KafkaTemplate<String, DirectMessageDto> kafkaTemplateForDirect;

    public void sendMessageForCommunity(CommunityMessageDto messageDto) {
        System.out.println("chatmessage = " + messageDto.getMessage());
        kafkaTemplateForCommunity.send(communityChatTopic, messageDto);
    }

    public void sendMessageForDirect(DirectMessageDto messageDto) {
        System.out.println("chatmessage = " + messageDto.getMessage());
        kafkaTemplateForDirect.send(directChatTopic, messageDto);
    }
}