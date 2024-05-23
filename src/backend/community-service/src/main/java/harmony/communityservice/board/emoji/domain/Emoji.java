package harmony.communityservice.board.emoji.domain;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.domain.Domain;
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
    public Emoji(BoardId boardId, EmojiId emojiId, Long emojiType, EmojiUser emojiUser) {
        this.boardId = boardId;
        this.emojiId = emojiId;
        this.emojiType = emojiType;
        this.emojiUsers.add(emojiUser);
    }

    public Emoji(BoardId boardId, EmojiId emojiId, Long emojiType, List<EmojiUser> emojiUsers) {
        this.boardId = boardId;
        this.emojiId = emojiId;
        this.emojiType = emojiType;
        this.emojiUsers = emojiUsers;
    }

    @Override
    public EmojiId getId() {
        return emojiId;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class EmojiId {
        private final Long id;

        public static EmojiId make(Long emojiId) {
            return new EmojiId(emojiId);
        }
    }
}
