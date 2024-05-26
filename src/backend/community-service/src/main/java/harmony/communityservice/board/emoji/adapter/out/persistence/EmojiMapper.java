package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.board.emoji.domain.Emoji;
import harmony.communityservice.board.emoji.domain.Emoji.EmojiId;
import harmony.communityservice.board.emoji.domain.EmojiUser;
import harmony.communityservice.board.emoji.domain.EmojiUser.EmojiUserId;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;

class EmojiMapper {

    static Emoji convert(EmojiEntity emojiEntity) {
        List<EmojiUser> emojiUsers = emojiEntity.getEmojiUsers().stream()
                .map(emojiUserEntity -> new EmojiUser(UserId.make(emojiUserEntity.getUserId().getId()),
                        EmojiUserId.make(emojiUserEntity.getEmojiUserId().getId())))
                .toList();
        return Emoji.builder()
                .boardId((BoardId.make(emojiEntity.getBoardId().getId())))
                .emojiId(EmojiId.make(emojiEntity.getEmojiId().getId()))
                .emojiType(emojiEntity.getEmojiType())
                .emojiUsers(emojiUsers)
                .build();

    }
}
