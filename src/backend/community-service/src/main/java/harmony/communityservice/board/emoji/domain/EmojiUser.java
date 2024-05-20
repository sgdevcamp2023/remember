package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.user.domain.User.UserId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EmojiUser {

    private final UserId userId;
    private EmojiUserId emojiUserId;

    public EmojiUser(UserId userId, EmojiUserId emojiUserId) {
        this.userId = userId;
        this.emojiUserId = emojiUserId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EmojiUserId {
        private final Long id;

        public static EmojiUserId make(Long emojiUserId) {
            return new EmojiUserId(emojiUserId);
        }
    }
}
