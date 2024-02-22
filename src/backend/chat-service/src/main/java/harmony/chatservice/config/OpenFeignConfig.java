package harmony.chatservice.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("harmony.chatservice.client")
class OpenFeignConfig {

}