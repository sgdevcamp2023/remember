package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.domain.EmojiUser;
import harmony.communityservice.community.query.dto.SearchEmojiResponse;
import java.util.ArrayList;
import java.util.List;

public class ToSearchEmojiResponseMapper {

    public static SearchEmojiResponse convert(Emoji emoji) {
        List<Long> userIds = new ArrayList<>();
        for (EmojiUser emojiUser : emoji.getEmojiUsers()) {
            userIds.add(emojiUser.getUserId());
        }

        return SearchEmojiResponse.builder()
                .emojiId(emoji.getEmojiId())
                .emojiUsers(userIds)
                .emojiType(emoji.getEmojiType())
                .boardId(emoji.getBoard().getBoardId())
                .build();
    }
}
