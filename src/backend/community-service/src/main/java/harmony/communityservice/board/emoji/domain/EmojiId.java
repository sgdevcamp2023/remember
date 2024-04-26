package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class EmojiId extends LongTypeIdentifier {

    public EmojiId(Long id) {
        super(id);
    }

    public Long getId() {
        return longValue();
    }

    public static EmojiId make(long id) {
        return new EmojiId(id);
    }

    public static class EmojiIdJavaType extends LongTypeIdentifierJavaType<EmojiId> {
        public EmojiIdJavaType() {
            super(EmojiId.class);
        }
    }
}
