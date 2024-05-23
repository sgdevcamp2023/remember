package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class BoardIdJpaVO extends LongTypeIdentifier {

    public BoardIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static BoardIdJpaVO make(Long id) {
        return new BoardIdJpaVO(id);
    }

    public static class BoardIdJavaType extends LongTypeIdentifierJavaType<BoardIdJpaVO> {
        public BoardIdJavaType() {
            super(BoardIdJpaVO.class);
        }
    }
}
