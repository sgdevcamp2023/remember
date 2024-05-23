package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class EmojiUserIdJpaVO extends EntityLongTypeIdentifier {
    public EmojiUserIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static EmojiUserIdJpaVO make(long id) {
        return new EmojiUserIdJpaVO(id);
    }

    public static class EmojiUserIdJavaType extends EntityLongTypeIdentifierJavaType<EmojiUserIdJpaVO> {
        public EmojiUserIdJavaType() {
            super(EmojiUserIdJpaVO.class);
        }
    }
}
