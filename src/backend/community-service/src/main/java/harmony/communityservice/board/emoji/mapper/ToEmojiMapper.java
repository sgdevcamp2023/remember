package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.emoji.domain.Emoji;

public class ToEmojiMapper {

    public static Emoji convert(Long boardId, long emojiType, long userId) {
        return Emoji.builder()
                .boardId(boardId)
                .emojiType(emojiType)
                .userId(userId)
                .build();
    }
}
