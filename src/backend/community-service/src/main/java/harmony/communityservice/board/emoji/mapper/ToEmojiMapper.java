package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiUser;
import harmony.communityservice.user.adapter.out.persistence.UserId;

public class ToEmojiMapper {

    public static Emoji convert(Long boardId, long emojiType, long userId) {
        return Emoji.builder()
                .boardId(BoardIdJpaVO.make(boardId))
                .emojiType(emojiType)
                .emojiUser(EmojiUser.make(UserId.make(userId)))
                .build();
    }
}
