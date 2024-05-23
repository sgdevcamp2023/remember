package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class RoomUserIdJpaVO extends LongTypeIdentifier {
    public RoomUserIdJpaVO(Long id) {
        super(id);
    }

    public static RoomUserIdJpaVO make(long id) {
        return new RoomUserIdJpaVO(id);
    }

    public static class RoomUserIdJavaType extends LongTypeIdentifierJavaType<RoomUserIdJpaVO> {
        public RoomUserIdJavaType(){
            super(RoomUserIdJpaVO.class);
        }
    }
}
