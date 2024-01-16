package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.query.dto.EmojiResponseDto;

public class ToEmojiResponseDtoMapper {

    public static EmojiResponseDto convert(Emoji emoji) {
        return EmojiResponseDto.builder()
                .emojiId(emoji.getEmojiId())
                .emojiUsers(emoji.getEmojiUsers())
                .commentId(emoji.getComment().getCommentId())
                .emojiType(emoji.getEmojiType())
                .boardId(emoji.getBoard().getBoardId())
                .build();
    }
}
