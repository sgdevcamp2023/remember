package harmony.communityservice.common.outbox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EventType {
    DELETED_GUILD("DELETE-GUILD"),
    DELETED_CHANNEL("DELETE-CHANNEL"),
    CREATED_GUILD("CREATE-GUILD"),
    CREATED_CHANNEL("CREATE-CHANNEL");

    private final String type;
}
