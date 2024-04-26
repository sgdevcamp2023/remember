package harmony.communityservice.board.board.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class BoardId extends LongTypeIdentifier {

    public BoardId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static BoardId make(Long id) {
        return new BoardId(id);
    }

    public static class BoardIdJavaType extends LongTypeIdentifierJavaType<BoardId> {
        public BoardIdJavaType() {
            super(BoardId.class);
        }
    }
}
