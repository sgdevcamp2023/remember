package harmony.chatservice.client;

import harmony.chatservice.dto.response.BaseResponseDto;
import harmony.chatservice.dto.response.SessionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "StateClient", url = "http://34.22.109.45:9000/api")
public interface StateClient {

    @PostMapping("/state/update/session")
    BaseResponseDto<String> updateSession(@RequestBody SessionDto sessionDto);
}