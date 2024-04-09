package harmony.communityservice.common.utils;

import org.slf4j.MDC;


public abstract class LogPrinter {
    public abstract void doLogging(String message);

    public void logging(String message) {
        this.doLogging(message);
        this.removeLogInfo();

    }

    protected void removeLogInfo() {
        MDC.remove("Trace-Id");
        MDC.remove("Api-Addr");
        MDC.remove("Http-Method");
        MDC.remove("User-Id");
    }
}
