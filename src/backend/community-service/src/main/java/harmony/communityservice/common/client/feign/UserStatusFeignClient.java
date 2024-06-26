package harmony.communityservice.common.client.feign;


import harmony.communityservice.common.config.HeaderConfig;
import harmony.communityservice.common.dto.LoadDmUserStateFeignResponse;
import harmony.communityservice.common.dto.LoadUserStateInGuildAndChannelFeignResponse;
import harmony.communityservice.guild.guild.adapter.in.web.LoadUserStatesInGuildRequest;
import harmony.communityservice.common.dto.LoadUserStatusInDmRoomRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "userStatus", url = "http://34.22.109.45:9000", configuration = HeaderConfig.class)
public interface UserStatusFeignClient {

    @PostMapping("/api/state/community/user/info")
    LoadUserStateInGuildAndChannelFeignResponse getCommunityUsersState(
            @RequestBody LoadUserStatesInGuildRequest searchUserStatesInGuildRequest);

    @PostMapping("/api/state/direct/user/info")
    LoadDmUserStateFeignResponse userStates(
            @RequestBody LoadUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest);
}
