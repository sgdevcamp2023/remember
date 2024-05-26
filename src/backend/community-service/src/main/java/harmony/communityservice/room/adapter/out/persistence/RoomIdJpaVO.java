package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;


public class RoomIdJpaVO extends EntityLongTypeIdentifier {

    public RoomIdJpaVO(Long id) {
        super(id);
    }

    public static RoomIdJpaVO make(long id) {
        return new RoomIdJpaVO(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class RoomIdJavaType extends EntityLongTypeIdentifierJavaType<RoomIdJpaVO> {
        public RoomIdJavaType() {
            super(RoomIdJpaVO.class);
        }
    }
}
