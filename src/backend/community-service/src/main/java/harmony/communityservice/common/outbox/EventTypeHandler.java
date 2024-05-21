package harmony.communityservice.common.outbox;

public class EventTypeHandler extends EnumTypeHandler<ExternalEventType>{
    public EventTypeHandler() {
        super(ExternalEventType.class);
    }
}
