package harmony.communityservice.board.emoji.mapper;

import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.EmojiUser;
import harmony.communityservice.board.emoji.dto.SearchEmojiResponse;
import harmony.communityservice.user.domain.UserId;

public class ToSearchEmojiResponseMapper {

    public static SearchEmojiResponse convert(Emoji emoji) {

        return SearchEmojiResponse.builder()
                .emojiId(emoji.getEmojiId().getId())
                .emojiUsers(emoji.getEmojiUsers().stream().map(EmojiUser::getUserId).map(UserId::getId).toList())
                .emojiType(emoji.getEmojiType())
                .boardId(emoji.getBoardId().getId())
                .build();
    }
}
