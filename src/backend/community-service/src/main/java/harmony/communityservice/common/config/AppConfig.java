package harmony.communityservice.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import harmony.communityservice.common.client.UserStatusClient;
import harmony.communityservice.common.client.feign.UserStatusFeignClient;
import harmony.communityservice.common.client.impl.UserStatusClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserStatusFeignClient userStatusFeignClient;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public UserStatusClient userStatusClient() {
        return new UserStatusClientImpl(userStatusFeignClient);
    }
}
