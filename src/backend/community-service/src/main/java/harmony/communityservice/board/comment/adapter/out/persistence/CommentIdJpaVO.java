package harmony.communityservice.board.comment.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class CommentIdJpaVO extends EntityLongTypeIdentifier {
    public CommentIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static CommentIdJpaVO make(long id) {
        return new CommentIdJpaVO(id);
    }

    public static class CommentIdJavaType extends EntityLongTypeIdentifierJavaType<CommentIdJpaVO> {
        public CommentIdJavaType() {
            super(CommentIdJpaVO.class);
        }
    }
}

