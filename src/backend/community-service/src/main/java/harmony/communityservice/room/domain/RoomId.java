package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;


public class RoomId extends LongTypeIdentifier {

    public RoomId(Long id) {
        super(id);
    }

    public static RoomId make(long id) {
        return new RoomId(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class RoomIdJavaType extends LongTypeIdentifierJavaType<RoomId> {
        public RoomIdJavaType() {
            super(RoomId.class);
        }
    }
}
