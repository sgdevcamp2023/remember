package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class RoomUserIdJpaVO extends EntityLongTypeIdentifier {
    public RoomUserIdJpaVO(Long id) {
        super(id);
    }

    public static class RoomUserIdJavaType extends EntityLongTypeIdentifierJavaType<RoomUserIdJpaVO> {
        public RoomUserIdJavaType(){
            super(RoomUserIdJpaVO.class);
        }
    }
}