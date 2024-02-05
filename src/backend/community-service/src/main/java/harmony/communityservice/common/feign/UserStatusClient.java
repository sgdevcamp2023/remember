package harmony.communityservice.common.feign;


import harmony.communityservice.community.query.dto.UserStatusRequestDto;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(name = "userStatus",url = "http://10.99.14.176:9090")
public interface UserStatusClient {

    @PostMapping("/api/state/user/connection")
    Map<Long, String> userStatus(@RequestBody UserStatusRequestDto requestDto);
}
