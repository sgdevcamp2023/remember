package harmony.stateservice.service;

import harmony.stateservice.dto.ConnectionStateRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServerService {

    private final RedisTemplate<String, Object> redisTemplate;

    public Map<Long, String> getUsersConnectionState(ConnectionStateRequest stateRequest) {

        String stateKey = "USER:STATE";
        List<Long> userIds = stateRequest.getUserIds();
        Map<Long, String> connectionStates = new HashMap<>();
        Map<Object, Object> stateInfos = redisTemplate.opsForHash().entries(stateKey);
        for (Long id : userIds) {
            String userId = id.toString();
            String state = stateInfos.get(userId) != null ? stateInfos.get(userId).toString() : "N";

            if (!state.equals("N")) {
                connectionStates.put(Long.parseLong(userId), state);
            }
        }

        return connectionStates;
    }
}