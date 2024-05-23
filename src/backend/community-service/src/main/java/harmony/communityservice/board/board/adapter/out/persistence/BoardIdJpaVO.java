package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class BoardIdJpaVO extends EntityLongTypeIdentifier {

    public BoardIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static BoardIdJpaVO make(Long id) {
        return new BoardIdJpaVO(id);
    }

    public static class BoardIdJavaType extends EntityLongTypeIdentifierJavaType<BoardIdJpaVO> {
        public BoardIdJavaType() {
            super(BoardIdJpaVO.class);
        }
    }
}
