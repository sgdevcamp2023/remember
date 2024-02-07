package harmony.chatservice.client;

import harmony.chatservice.dto.response.BaseResponseDto;
import harmony.chatservice.dto.response.SessionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "StateClient", url = "http://10.99.14.176:9090/api")
public interface StateClient {

    @PostMapping("/state/update/session")
    BaseResponseDto<String> updateSession(@RequestBody SessionDto sessionDto);
}