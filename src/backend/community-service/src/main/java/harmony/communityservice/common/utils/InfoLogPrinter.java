package harmony.communityservice.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InfoLogPrinter extends LogPrinter {
    @Override
    public void logging(HttpServletRequest request, String message) {
        putLogInfo(request);
        log.info(message);
        removeLogInfo();
    }

    @Override
    protected void removeLogInfo() {
        super.removeLogInfo();
    }

    @Override
    protected void putLogInfo(HttpServletRequest request) {
        super.putLogInfo(request);
    }
}
