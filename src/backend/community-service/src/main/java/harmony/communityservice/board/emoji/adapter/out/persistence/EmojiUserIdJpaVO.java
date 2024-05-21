package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class EmojiUserIdJpaVO extends LongTypeIdentifier {
    public EmojiUserIdJpaVO(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static EmojiUserIdJpaVO make(long id) {
        return new EmojiUserIdJpaVO(id);
    }

    public static class EmojiUserIdJavaType extends LongTypeIdentifierJavaType<EmojiUserIdJpaVO> {
        public EmojiUserIdJavaType() {
            super(EmojiUserIdJpaVO.class);
        }
    }
}
