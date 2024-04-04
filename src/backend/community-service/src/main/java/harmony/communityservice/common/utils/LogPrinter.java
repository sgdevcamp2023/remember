package harmony.communityservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


public abstract class LogPrinter {
    public abstract void logging(HttpServletRequest request, String message);


    protected void removeLogInfo() {
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }

    protected void putLogInfo(HttpServletRequest request) {
        MDC.put("Trace-Id", request.getHeader("trace-id"));
        MDC.put("Api-Addr", request.getRequestURI());
        MDC.put("Http-Method", request.getMethod());
        MDC.put("User-Id", request.getHeader("user-id"));
    }
}
