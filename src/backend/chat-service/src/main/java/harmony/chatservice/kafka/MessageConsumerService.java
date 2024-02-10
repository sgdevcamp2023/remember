package harmony.chatservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.chatservice.dto.CommunityEventDto;
import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.DirectMessageDto;
import harmony.chatservice.dto.EmojiDto;
import harmony.chatservice.dto.response.ChannelEventDto;
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

    @KafkaListener(topics = "${spring.kafka.topic.community-chat}", groupId = "${spring.kafka.consumer.group-id.community}", containerFactory = "communityListener")
    public void consumeForCommunity(CommunityMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/guild/" + messageDto.getGuildId(), messageDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.direct-chat}", groupId = "${spring.kafka.consumer.group-id.direct}", containerFactory = "directListener")
    public void consumeForDirect(DirectMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/direct/" + messageDto.getRoomId(), messageDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.emoji-chat}", groupId = "${spring.kafka.consumer.group-id.emoji}", containerFactory = "emojiListener")
    public void consumeForEmoji(EmojiDto emojiDto){
        if (emojiDto.getRoomId() > 0) {
            messagingTemplate.convertAndSend("/topic/direct/" + emojiDto.getRoomId(), emojiDto);
        }
        if (emojiDto.getGuildId() > 0) {
            messagingTemplate.convertAndSend("/topic/guild/" + emojiDto.getGuildId(), emojiDto);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.connection-event}", groupId = "${spring.kafka.consumer.group-id.connection-event}", containerFactory = "connectionEventListener")
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

    @KafkaListener(topics = "${spring.kafka.topic.community-event}", groupId = "${spring.kafka.consumer.group-id.community-event}", containerFactory = "communityEventListener")
    public void consumeForCommunityEvent(CommunityEventDto eventDto) throws JsonProcessingException {
        HashMap<String,String> communityEventInfo = new HashMap<>();
        if (eventDto.getEventType().equals("DELETE-GUILD")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("eventType", "DELETE-GUILD");
        } else if (eventDto.getEventType().equals("CREATE-CHANNEL")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("eventType", "CREATE-CHANNEL");
            communityEventInfo.put("channelType", eventDto.getChannelType());
            communityEventInfo.put("channelReadId", String.valueOf(eventDto.getChannelReadId()));
            communityEventInfo.put("categoryId", String.valueOf(eventDto.getCategoryId()));
        } else if (eventDto.getEventType().equals("DELETE-CHANNEL")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("eventType", "DELETE-CHANNEL");
            communityEventInfo.put("channelReadId", String.valueOf(eventDto.getChannelReadId()));
        }
        String communityEvent = objectMapper.writeValueAsString(communityEventInfo);

        messagingTemplate.convertAndSend("/topic/guild/" + eventDto.getGuildId(), communityEvent);
    }

    @KafkaListener(topics = "${spring.kafka.topic.channel-event}", groupId = "${spring.kafka.consumer.group-id.channel-event}", containerFactory = "channelEventListener")
    public void consumeForChannelEvent(ChannelEventDto eventDto) {
        if (eventDto.getType().equals("JOIN") || eventDto.getType().equals("LEAVE")) {
            messagingTemplate.convertAndSend("/topic/guild/" + eventDto.getGuildId(), eventDto);
        }
    }
}