package harmony.chatservice.client;

import harmony.chatservice.dto.response.CommunityFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CommunityClient", url = "http://34.22.109.45:4040/api")
public interface CommunityClient {

    @GetMapping("/community/check/room/guild/{userId}")
    CommunityFeignResponse getGuildAndRoomIds(@PathVariable(value = "userId") Long userId);
}