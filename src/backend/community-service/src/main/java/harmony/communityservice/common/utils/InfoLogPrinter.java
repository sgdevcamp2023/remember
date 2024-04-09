package harmony.communityservice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InfoLogPrinter extends LogPrinter {
    @Override
    public void doLogging(String message) {
        log.info(message);
    }
}
