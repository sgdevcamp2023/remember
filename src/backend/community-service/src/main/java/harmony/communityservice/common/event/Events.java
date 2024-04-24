package harmony.communityservice.common.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
public class Events {

    private static ApplicationEventPublisher publisher;

    public static void register(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void send(Object event) {
        if (publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
