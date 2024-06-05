package harmony.communityservice.common.config;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class HeaderConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
                requestTemplate.header("trace-id", request.getHeader("trace-id"));
            }
        };
    }
}

