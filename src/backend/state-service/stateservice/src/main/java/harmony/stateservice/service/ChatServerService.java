package harmony.stateservice.service;

import harmony.stateservice.dto.SessionDto;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, Object> map = new HashMap<>();
        String userId = null;
        if (sessionDto.getType().equals("CONNECT")) {
            userId = String.valueOf(sessionDto.getUserId());
            map.put("state", sessionDto.getState());
            hashOperations.putAll(userId, map);
            saveSessionId(sessionId, userId);
        }
        if (sessionDto.getType().equals("DISCONNECT")) {
            userId = getUserId(sessionId);
            map.put("state", sessionDto.getState());
            hashOperations.putAll(userId, map);
            deleteSessionId(sessionId);
        }

        log.info("userId {}", userId);
        log.info("sessionId {}", sessionDto.getSessionId());
        log.info("user state {}", hashOperations.get(userId, "state"));
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