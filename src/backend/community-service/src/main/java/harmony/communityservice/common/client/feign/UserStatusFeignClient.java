package harmony.communityservice.common.client.feign;


import harmony.communityservice.common.config.HeaderConfig;
import harmony.communityservice.common.dto.SearchDmUserStateFeignResponse;
import harmony.communityservice.common.dto.SearchUserStateInGuildAndRoomFeignResponse;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildRequest;
import harmony.communityservice.room.dto.SearchUserStatusInDmRoomRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "userStatus", url = "http://34.22.109.45:9000", configuration = HeaderConfig.class)
public interface UserStatusFeignClient {

    @PostMapping("/api/state/community/user/info")
    SearchUserStateInGuildAndRoomFeignResponse userStatus(
            @RequestBody SearchUserStatesInGuildRequest searchUserStatesInGuildRequest);

    @PostMapping("/api/state/direct/user/info")
    SearchDmUserStateFeignResponse getCommunityUsersState(
            @RequestBody SearchUserStatusInDmRoomRequest searchUserStatusInDmRoomRequest);
}
