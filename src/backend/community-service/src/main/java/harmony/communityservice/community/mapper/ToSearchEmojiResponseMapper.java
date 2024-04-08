package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.dto.SearchEmojiResponse;

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
