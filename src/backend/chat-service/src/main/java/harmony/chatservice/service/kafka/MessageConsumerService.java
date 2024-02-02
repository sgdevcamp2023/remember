package harmony.chatservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.response.ConnectionEventDto;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final ObjectMapper objectMapper;

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "communityChatTopic", groupId = "communityGroup", containerFactory = "communityListener")
    public void consumeForCommunity(CommunityMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/guild/" + messageDto.getGuildId(), messageDto);
    }

    @KafkaListener(topics = "directChatTopic", groupId = "directGroup", containerFactory = "directListener")
    public void consumeForDirect(DirectMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/direct/" + messageDto.getRoomId(), messageDto);
    }

    @KafkaListener(topics = "emojiChatTopic", groupId = "emojiGroup", containerFactory = "emojiListener")
    public void consumeForEmoji(EmojiDto emojiDto){
        if (emojiDto.getRoomId() > 0) {
            messagingTemplate.convertAndSend("/topic/direct/" + emojiDto.getRoomId(), emojiDto);
        }
        if (emojiDto.getGuildId() > 0) {
            messagingTemplate.convertAndSend("/topic/guild/" + emojiDto.getGuildId(), emojiDto);
        }
    }

    @KafkaListener(topics = "stateChatTopic", groupId = "stateGroup", containerFactory = "connectionEventListener")
    public void consumeForConnectionEvent(ConnectionEventDto connectionEventDto) throws JsonProcessingException {
        if (connectionEventDto.getType().equals("CONNECT") || connectionEventDto.getType().equals("DISCONNECT")) {
            HashMap<String,String> stateInfo = new HashMap<>();
            stateInfo.put("state", connectionEventDto.getState());
            stateInfo.put("userId", String.valueOf(connectionEventDto.getUserId()));
            String connectionEvent = objectMapper.writeValueAsString(stateInfo);

            if (connectionEventDto.getGuildIds() != null) {
                for (Long guildId : connectionEventDto.getGuildIds()) {
                    messagingTemplate.convertAndSend("/topic/guild/" + guildId, connectionEvent);
                }
            }
            if (connectionEventDto.getRoomIds() != null) {
                for (Long roomId : connectionEventDto.getRoomIds()) {
                    messagingTemplate.convertAndSend("/topic/direct/" + roomId, connectionEvent);
                }
            }
        }
    }
}