package harmony.chatservice.config;

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
    private static final String AUTH_PREFIX = "Authorization";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
//            if (!jwtTokenHandler.verifyToken(Objects.requireNonNull(headerAccessor.getFirstNativeHeader(AUTH_PREFIX)))) {
//                throw new RuntimeException("예외 발생");
//            }
//        }

        log.info("================================");
        log.info("getSessionId {}", headerAccessor.getSessionId());
        log.info("getSubscriptionId {}", headerAccessor.getSubscriptionId());
        log.info("getMessageHeaders {}", headerAccessor.getMessageHeaders());
        log.info("getCommand {}", headerAccessor.getCommand());
        log.info("================================");
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.DISCONNECT.equals(headerAccessor.getCommand())) {
            // String userId = headerAccessor.getFirstNativeHeader("user-id");
            String sessionId = headerAccessor.getSessionId();
            // log.info("userId {}", userId);
            log.info("sessionId {}", sessionId);
        }
    }
}