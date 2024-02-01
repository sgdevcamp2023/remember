package harmony.communityservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LoggingUtils {

    public static void errorLogging(HttpServletRequest request, String exception) {
        String apiAddr = request.getRequestURI();
        String traceId = request.getHeader("trace-id");
        String userId = request.getHeader("user-id");
        String httpMethod = request.getMethod();
        MDC.put("Trace-Id", traceId);
        MDC.put("Api-Addr", apiAddr);
        MDC.put("Http-Method", httpMethod);
        MDC.put("User-Id", userId);
        log.error(exception);
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }
}
