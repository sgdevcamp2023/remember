package harmony.communityservice.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RegisterLogInfoInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        MDC.put("Trace-Id", request.getHeader("trace-id"));
        MDC.put("Api-Addr", request.getRequestURI());
        MDC.put("Http-Method", request.getMethod());
        MDC.put("User-Id", request.getHeader("user-id"));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }
}
