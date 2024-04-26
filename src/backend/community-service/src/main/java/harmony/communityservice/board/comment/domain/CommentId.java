package harmony.communityservice.board.comment.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class CommentId extends LongTypeIdentifier {
    public CommentId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CommentId make(long id) {
        return new CommentId(id);
    }

    public static class CommentIdJavaType extends LongTypeIdentifierJavaType<CommentId> {
        public CommentIdJavaType() {
            super(CommentId.class);
        }
    }
}

