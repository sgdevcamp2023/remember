package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
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
        verifyUserId(userId);
        this.userId = userId;
        verifyEmojiUserId(emojiUserId);
        this.emojiUserId = emojiUserId;
    }

    @Override
    public EmojiUserId getId() {
        return emojiUserId;
    }

    private void verifyEmojiUserId(EmojiUserId emojiUserId) {
        if (emojiUserId != null && emojiUserId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("emojiUserId의 범위가 1 미만입니다.");
        }
    }

    private void verifyUserId(UserId userId) {
        if (userId == null) {
            throw new NotFoundDataException("userId가 존재하지 않습니다");
        }

        if (userId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("userId의 범위가 1 미만입니다.");
        }
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
