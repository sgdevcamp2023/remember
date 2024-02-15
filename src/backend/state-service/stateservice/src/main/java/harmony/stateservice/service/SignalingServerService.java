package harmony.stateservice.service;

import harmony.stateservice.dto.ChannelEventDto;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignalingServerService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String GUILD = "GUILD";
    private final String SEP = ":";
    private final long TIME = 60 * 1000L;

    public void updateChannelEvent(ChannelEventDto channelEventDto) {
        HashOperations<String, String, Set<String>> hashOperations = redisTemplate.opsForHash();
        String hashKey = GUILD + SEP + channelEventDto.getGuildId();
        String channelId = String.valueOf(channelEventDto.getChannelId());

        Set<String> userIds = hashOperations.get(hashKey, channelId);
        if (channelEventDto.getType().equals("JOIN")) {
            if (userIds != null) {
                userIds.add(channelEventDto.getUserId());
                hashOperations.put(hashKey, channelId, userIds);
            } else {
                Set<String> joinedUserIds = new HashSet<>();
                joinedUserIds.add(channelEventDto.getUserId());
                hashOperations.put(hashKey, channelId, joinedUserIds);
            }
        } else if (channelEventDto.getType().equals("LEAVE")) {
            if (userIds != null) {
                userIds.remove(channelEventDto.getUserId());
                hashOperations.put(hashKey, channelId, userIds);
            }
        }

        redisTemplate.expire(hashKey, TIME, TimeUnit.SECONDS);
    }
}