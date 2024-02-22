package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;

public class ToEmojiMapper {

    public static Emoji convert(Board board, long emojiType) {
        return Emoji.builder()
                .board(board)
                .emojiType(emojiType)
                .build();
    }
}
