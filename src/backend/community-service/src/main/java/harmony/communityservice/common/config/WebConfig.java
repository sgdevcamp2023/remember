package harmony.communityservice.common.config;

import harmony.communityservice.common.interceptor.RegisterLogInfoInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        RegisterLogInfoInterceptor registerLogInfoInterceptor = new RegisterLogInfoInterceptor();
        registry.addInterceptor(registerLogInfoInterceptor)
                .addPathPatterns("/**");
    }
}
