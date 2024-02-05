package harmony.stateservice.service;

import harmony.stateservice.dto.UserStateDto;
import harmony.stateservice.dto.request.UserStateRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityServerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String GUILD = "GUILD";
    private final String SEP = ":";

    public UserStateDto getUsersState(UserStateRequest stateRequest) {
        Map<Long, String> connectionStates = getConnectionState(stateRequest.getUserIds());
        Map<String, Set<String>> channelStates = getChannelState(stateRequest.getGuildId());
        return new UserStateDto(connectionStates, channelStates);
    }

    public Map<Long, String> getConnectionState(List<Long> userIds) {
        String stateKey = "USER:STATE";
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

    public Map<String, Set<String>> getChannelState(Long guildId) {
        HashOperations<String, String, Set<String>> hashOperations = redisTemplate.opsForHash();
        String hashKey = GUILD + SEP + guildId;
        return hashOperations.entries(hashKey);
    }
}