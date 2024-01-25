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
public class StateService {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveSessionInfo(SessionDto sessionDto) {
        saveSessionId(sessionDto);
        saveState(sessionDto);
    }

    public void saveState(SessionDto sessionDto) {
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String userId = String.valueOf(sessionDto.getUserId());
        Map<String, Object> map = new HashMap<>();
        map.put("state", sessionDto.getState());
        hashOperations.putAll(userId, map);
        log.info("save user state {}", hashOperations.get(userId, "state"));
    }

    public void saveSessionId(SessionDto sessionDto) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String userId = String.valueOf(sessionDto.getUserId());
        valueOperations.set(sessionDto.getSessionId(), userId);
        log.info("save getSessionId {}", valueOperations.get(sessionDto.getSessionId()));
    }
}