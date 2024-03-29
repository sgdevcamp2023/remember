package harmony.communityservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LoggingUtils {
    public static void printLog(HttpServletRequest request, String message, boolean type) {
        String apiPath = request.getRequestURI();
        String traceId = request.getHeader("trace-id");
        String userId = request.getHeader("user-id");
        String requestHttpMethod = request.getMethod();
        MDC.put("Trace-Id", traceId);
        MDC.put("Api-Addr", apiPath);
        MDC.put("Http-Method", requestHttpMethod);
        MDC.put("User-Id", userId);
        if (type) {
            log.info(message);
        }else{
            log.error(message);
        }
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }
}
