package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.board.domain.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiUser;
import harmony.communityservice.user.domain.UserId;

public class ToEmojiMapper {

    public static Emoji convert(Long boardId, long emojiType, long userId) {
        return Emoji.builder()
                .boardId(BoardId.make(boardId))
                .emojiType(emojiType)
                .emojiUser(EmojiUser.make(UserId.make(userId)))
                .build();
    }
}
