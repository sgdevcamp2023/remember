package harmony.chatservice.config;

import harmony.chatservice.client.CommunityClient;
import harmony.chatservice.client.StateClient;
import harmony.chatservice.dto.response.CommunityFeignResponse;
import harmony.chatservice.dto.response.SessionDto;
import harmony.chatservice.dto.response.ConnectionEventDto;
import harmony.chatservice.kafka.MessageProducerService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilterInboundChannel implements ChannelInterceptor {

    private final JwtTokenHandler jwtTokenHandler;
    private final StateClient stateClient;
    private final CommunityClient communityClient;
    private final MessageProducerService messageService;
    private static final String AUTH_PREFIX = "Authorization";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
//            if (!jwtTokenHandler.verifyToken(Objects.requireNonNull(headerAccessor.getFirstNativeHeader(AUTH_PREFIX)))) {
//                throw new RuntimeException("예외 발생");
//            }
//        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            Long userId = Long.parseLong(Objects.requireNonNull(headerAccessor.getFirstNativeHeader("userId")));
            String sessionId = headerAccessor.getSessionId();
            SessionDto sessionDto = SessionDto.builder()
                    .userId(userId)
                    .sessionId(sessionId)
                    .type("CONNECT")
                    .state("online")
                    .build();
            messageService.sendMessageForSession(sessionDto);

            CommunityFeignResponse ids = communityClient.getGuildAndRoomIds(userId);
            ConnectionEventDto connectionEventDto = ConnectionEventDto.builder()
                    .userId(userId)
                    .type("CONNECT")
                    .state("online")
                    .guildIds(ids.getResultData().getGuildIds())
                    .roomIds(ids.getResultData().getRoomIds())
                    .build();
            messageService.sendMessageForConnectionEvent(connectionEventDto);
        }

        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            String sessionId = headerAccessor.getSessionId();
            SessionDto sessionDto = SessionDto.builder()
                    .sessionId(sessionId)
                    .type("DISCONNECT")
                    .state("offline")
                    .build();

            String checkUserId = stateClient.updateSession(sessionDto).getResult();
            if (checkUserId != null) {
                Long userId = Long.parseLong(checkUserId);

                CommunityFeignResponse ids = communityClient.getGuildAndRoomIds(userId);
                ConnectionEventDto connectionEventDto = ConnectionEventDto.builder()
                        .userId(userId)
                        .type("DISCONNECT")
                        .state("offline")
                        .guildIds(ids.getResultData().getGuildIds())
                        .roomIds(ids.getResultData().getRoomIds())
                        .build();
                messageService.sendMessageForConnectionEvent(connectionEventDto);
            }
        }
    }
}