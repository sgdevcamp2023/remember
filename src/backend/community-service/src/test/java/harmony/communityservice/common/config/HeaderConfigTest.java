package harmony.communityservice.common.config;

import static org.junit.jupiter.api.Assertions.*;

import feign.RequestTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class HeaderConfigTest {

    private HeaderConfig headerConfig;
    private MockHttpServletRequest servletRequest;

    @BeforeEach
    public void setUp() {
        headerConfig = new HeaderConfig();
        servletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(servletRequest));
    }

    @Test
    @DisplayName("RequestInterceptor 테스트")
    void request_interceptor() {
        String traceId = "test-trace-id";
        servletRequest.addHeader("trace-id", traceId);

        RequestTemplate requestTemplate = new RequestTemplate();
        headerConfig.requestInterceptor().apply(requestTemplate);

        assertEquals(traceId, requestTemplate.headers().get("trace-id").iterator().next());
    }
}