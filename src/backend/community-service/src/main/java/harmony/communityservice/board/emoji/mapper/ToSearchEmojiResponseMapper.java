package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.dto.SearchEmojiResponse;

public class ToSearchEmojiResponseMapper {

    public static SearchEmojiResponse convert(Emoji emoji) {

        return SearchEmojiResponse.builder()
                .emojiId(emoji.getEmojiId())
                .emojiUsers(emoji.getUserIds().stream().toList())
                .emojiType(emoji.getEmojiType())
                .boardId(emoji.getBoardId())
                .build();
    }
}
