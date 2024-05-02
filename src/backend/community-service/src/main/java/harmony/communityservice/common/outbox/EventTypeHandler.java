package harmony.communityservice.common.outbox;

public class EventTypeHandler extends EnumTypeHandler<EventType>{
    public EventTypeHandler() {
        super(EventType.class);
    }
}
