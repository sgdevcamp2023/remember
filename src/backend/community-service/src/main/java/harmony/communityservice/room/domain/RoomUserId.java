package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class RoomUserId extends LongTypeIdentifier {
    public RoomUserId(Long id) {
        super(id);
    }

    public static RoomUserId make(long id) {
        return new RoomUserId(id);
    }

    public static class RoomUserIdJavaType extends LongTypeIdentifierJavaType<RoomUserId> {
        public RoomUserIdJavaType(){
            super(RoomUserId.class);
        }
    }
}
