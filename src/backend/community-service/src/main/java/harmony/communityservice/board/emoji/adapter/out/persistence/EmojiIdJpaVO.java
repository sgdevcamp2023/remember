package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class EmojiIdJpaVO extends LongTypeIdentifier {

    public EmojiIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static EmojiIdJpaVO make(long id) {
        return new EmojiIdJpaVO(id);
    }

    public static class EmojiIdJavaType extends LongTypeIdentifierJavaType<EmojiIdJpaVO> {
        public EmojiIdJavaType() {
            super(EmojiIdJpaVO.class);
        }
    }
}
