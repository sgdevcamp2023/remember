package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class CommentIdJpaVO extends LongTypeIdentifier {
    public CommentIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CommentIdJpaVO make(long id) {
        return new CommentIdJpaVO(id);
    }

    public static class CommentIdJavaType extends LongTypeIdentifierJavaType<CommentIdJpaVO> {
        public CommentIdJavaType() {
            super(CommentIdJpaVO.class);
        }
    }
}

