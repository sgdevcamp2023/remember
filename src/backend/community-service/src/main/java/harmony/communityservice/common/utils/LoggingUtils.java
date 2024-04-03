package harmony.communityservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Slf4j
public class LoggingUtils {
    public static void printLog(HttpServletRequest request, String message, boolean type) {
        MDC.put("Trace-Id", request.getHeader("trace-id"));
        MDC.put("Api-Addr", request.getRequestURI());
        MDC.put("Http-Method", request.getMethod());
        MDC.put("User-Id", request.getHeader("user-id"));
        if (type) {
            infoLog(message);
        } else {
            errorLog(message);
        }
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }

    private static void errorLog(String message) {
        log.error(message);
    }

    private static void infoLog(String message) {
        log.info(message);
    }
}
