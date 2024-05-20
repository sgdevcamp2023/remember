package harmony.communityservice.board.emoji.application.service;

import harmony.communityservice.board.emoji.application.port.in.SearchEmojiResponse;
import harmony.communityservice.board.emoji.domain.Emoji;
import java.util.List;

class SearchEmojiResponseMapper {

    public static SearchEmojiResponse convert(Emoji emoji) {

        List<Long> emojiUsers = emoji.getEmojiUsers().stream()
                .map(emojiUser -> emojiUser.getUserId().getId())
                .toList();

        return SearchEmojiResponse.builder()
                .emojiId(emoji.getEmojiId().getId())
                .emojiUsers(emojiUsers)
                .emojiType(emoji.getEmojiType())
                .boardId(emoji.getBoardId().getId())
                .build();
    }
}
