package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class EmojiUser extends Domain<EmojiUser, EmojiUserId> {

    private final UserId userId;
    private EmojiUserId emojiUserId;

    public EmojiUser(UserId userId, EmojiUserId emojiUserId) {
        this.userId = userId;
        this.emojiUserId = emojiUserId;
    }

    @Override
    public EmojiUserId getId() {
        return emojiUserId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EmojiUserId extends ValueObject<EmojiUserId> {
        private final Long id;

        public static EmojiUserId make(Long emojiUserId) {
            return new EmojiUserId(emojiUserId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}
