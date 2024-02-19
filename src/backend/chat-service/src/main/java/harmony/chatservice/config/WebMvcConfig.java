package harmony.chatservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드를 허용한다.
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedOrigins("http://10.99.19.2:3000")
                .allowedOrigins("http://10.99.29.133:3000")
                .allowedOrigins("https://localhost:3000");
    }
}
