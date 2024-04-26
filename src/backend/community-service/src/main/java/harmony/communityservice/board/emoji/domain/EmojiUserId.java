package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class EmojiUserId extends LongTypeIdentifier {
    public EmojiUserId(Long id) {
        super(id);
    }

    public static EmojiUserId make(long id) {
        return new EmojiUserId(id);
    }

    public static class EmojiUserIdJavaType extends LongTypeIdentifierJavaType<EmojiUserId> {
        public EmojiUserIdJavaType() {
            super(EmojiUserId.class);
        }
    }
}
