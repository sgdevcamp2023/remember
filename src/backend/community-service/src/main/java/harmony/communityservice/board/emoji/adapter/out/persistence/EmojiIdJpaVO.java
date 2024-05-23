package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class EmojiIdJpaVO extends EntityLongTypeIdentifier {

    public EmojiIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static EmojiIdJpaVO make(long id) {
        return new EmojiIdJpaVO(id);
    }

    public static class EmojiIdJavaType extends EntityLongTypeIdentifierJavaType<EmojiIdJpaVO> {
        public EmojiIdJavaType() {
            super(EmojiIdJpaVO.class);
        }
    }
}
