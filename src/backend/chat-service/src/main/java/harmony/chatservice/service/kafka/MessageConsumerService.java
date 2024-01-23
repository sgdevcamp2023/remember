package harmony.chatservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.response.StateDto;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

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

    @KafkaListener(topics = "stateChatTopic", groupId = "stateGroup", containerFactory = "stateListener")
    public void consumeForState(StateDto stateDto) throws JsonProcessingException {
        if (stateDto.getType().equals("CONNECT") || stateDto.getType().equals("DISCONNECT")) {
            HashMap<String,String> stateInfo = new HashMap<>();
            stateInfo.put("state",stateDto.getState());
            stateInfo.put("userId", String.valueOf(stateDto.getUserId()));
            String userState = objectMapper.writeValueAsString(stateInfo);

            for (Long guildId : stateDto.getGuildIds()) {
                messagingTemplate.convertAndSend("/topic/guild/" + guildId, userState);
            }
            for (Long roomId : stateDto.getRoomIds()) {
                messagingTemplate.convertAndSend("/topic/direct/" + roomId, userState);
            }
        }
    }
}