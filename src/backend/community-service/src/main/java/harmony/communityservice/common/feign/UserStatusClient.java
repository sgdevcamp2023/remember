package harmony.communityservice.common.feign;


import harmony.communityservice.common.config.HeaderConfig;
import harmony.communityservice.common.dto.DmUserStateFeignResponseDto;
import harmony.communityservice.common.dto.UserStateFeignResponseDto;
import harmony.communityservice.community.query.dto.DmUserStatesRequestDto;
import harmony.communityservice.community.query.dto.UserStatusRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "userStatus", url = "http://10.99.14.176:9090", configuration = HeaderConfig.class)
public interface UserStatusClient {

    @PostMapping("/api/state/community/user/info")
    UserStateFeignResponseDto userStatus(@RequestBody UserStatusRequestDto requestDto);

    @PostMapping("/api/state/direct/user/info")
    DmUserStateFeignResponseDto getCommunityUsersState(@RequestBody DmUserStatesRequestDto requestDto);
}
