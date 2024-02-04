package harmony.stateservice.service;

import harmony.stateservice.dto.SessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServerService {

    private final RedisTemplate<String, Object> redisTemplate;

    public String updateSession(SessionDto sessionDto) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String sessionId = sessionDto.getSessionId();
        String stateKey = "USER:STATE";
        String userId = null;
        if (sessionDto.getType().equals("CONNECT")) {
            userId = String.valueOf(sessionDto.getUserId());
            hashOperations.put(stateKey, userId, sessionDto.getState());
            saveSessionId(sessionId, userId);
        }
        if (sessionDto.getType().equals("DISCONNECT")) {
            userId = getUserId(sessionId);
            hashOperations.put(stateKey, userId, sessionDto.getState());
            deleteSessionId(sessionId);
        }

        log.info("userId {}", userId);
        log.info("user state {}", hashOperations.get(stateKey, userId));
        return userId;
    }

    public void saveSessionId(String sessionId, String userId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(sessionId, userId);
    }

    public void deleteSessionId(String sessionId) {
        redisTemplate.delete(sessionId);
    }

    public String getUserId(String sessionId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(sessionId).toString();
    }
}