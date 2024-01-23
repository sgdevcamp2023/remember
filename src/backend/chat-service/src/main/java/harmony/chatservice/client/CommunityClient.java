package harmony.chatservice.client;

import harmony.chatservice.dto.response.CommunityFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CommunityClient", url = "http://10.99.19.2:8000/api")
public interface CommunityClient {

    @GetMapping("/community/check/room/guild/{userId}")
    CommunityFeignResponse getGuildAndRoomIds(@PathVariable(value = "userId") Long userId);
}