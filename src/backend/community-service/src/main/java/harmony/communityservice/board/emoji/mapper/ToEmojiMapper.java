package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.domain.Emoji;

public class ToEmojiMapper {

    public static Emoji convert(Long boardId, long emojiType, long userId) {
        return Emoji.builder()
                .boardId(boardId)
                .emojiType(emojiType)
                .userId(userId)
                .build();
    }
}
