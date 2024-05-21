package harmony.communityservice.room.adapter.out.persistence;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;


public class RoomIdJpaVO extends LongTypeIdentifier {

    public RoomIdJpaVO(Long id) {
        super(id);
    }

    public static RoomIdJpaVO make(long id) {
        return new RoomIdJpaVO(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class RoomIdJavaType extends LongTypeIdentifierJavaType<RoomIdJpaVO> {
        public RoomIdJavaType() {
            super(RoomIdJpaVO.class);
        }
    }
}
