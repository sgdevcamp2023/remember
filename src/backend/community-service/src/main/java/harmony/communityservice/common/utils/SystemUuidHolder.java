package harmony.communityservice.common.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
class SystemUuidHolder implements UuidHolder{
    @Override
    public String random() {
        System.out.println("true = " + true);
        return UUID.randomUUID().toString();
    }
}
