package harmony.communityservice.board.emoji.domain;

import static harmony.communityservice.board.emoji.domain.EmojiTypeRange.MAX;
import static harmony.communityservice.board.emoji.domain.EmojiTypeRange.MIN;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.ValueObject;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Emoji extends Domain<Emoji, EmojiId> {

    private EmojiId emojiId;

    private BoardId boardId;

    private Long emojiType;

    private List<EmojiUser> emojiUsers = new ArrayList<>();

    @Builder
    public Emoji(BoardId boardId, EmojiId emojiId, Long emojiType, List<EmojiUser> emojiUsers) {
        verifyBoardId(boardId);
        this.boardId = boardId;
        this.emojiId = emojiId;
        verifyEmojiType(emojiType);
        this.emojiType = emojiType;
        verifyEmojiUsers(emojiUsers);
        this.emojiUsers = emojiUsers;
    }

    private void verifyBoardId(BoardId boardId) {
        if (boardId == null) {
            throw new NotFoundDataException("BoardId를 찾을 수 없습니다");
        }
    }

    private void verifyEmojiType(Long emojiType) {
        if (emojiType == null) {
            throw new NotFoundDataException("EmojiType을 찾을 수 없습니다");
        }

        if (MIN.getValue() > emojiType || MAX.getValue() < emojiType) {
            throw new WrongThresholdRangeException("emoji 범위가 잘못되었습니다");
        }
    }

    private void verifyEmojiUsers(List<EmojiUser> emojiUsers) {
        if (emojiUsers == null || emojiUsers.isEmpty()) {
            throw new NotFoundDataException("EmojiUser를 찾을 수 없습니다");
        }
    }

    @Override
    public EmojiId getId() {
        return emojiId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EmojiId extends ValueObject<EmojiId> {
        private final Long id;

        public static EmojiId make(Long emojiId) {
            return new EmojiId(emojiId);
        }

        @Override
        protected Object[] getEqualityFields() {
            return new Object[]{id};
        }
    }
}
