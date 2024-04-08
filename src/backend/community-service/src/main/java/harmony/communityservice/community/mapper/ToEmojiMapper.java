package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Emoji;

public class ToEmojiMapper {

    public static Emoji convert(Long boardId, long emojiType, long userId) {
        return Emoji.builder()
                .boardId(boardId)
                .emojiType(emojiType)
                .userId(userId)
                .build();
    }
}
