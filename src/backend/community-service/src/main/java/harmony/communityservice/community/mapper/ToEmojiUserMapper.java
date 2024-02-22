package harmony.communityservice.community.mapper;

import harmony.communityservice.community.domain.Emoji;
import harmony.communityservice.community.domain.EmojiUser;

public class ToEmojiUserMapper {

    public static EmojiUser convert(Emoji emoji, long userId) {
        return EmojiUser.builder()
                .emoji(emoji)
                .userId(userId)
                .build();
    }
}
