package harmony.communityservice.board.emoji.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.BoardIdJpaVO;
import harmony.communityservice.board.board.domain.Board.BoardId;
import harmony.communityservice.user.adapter.out.persistence.UserIdJpaVO;
import harmony.communityservice.user.domain.User.UserId;

class EmojiEntityMapper {

    static EmojiEntity convert(BoardId boardId, UserId userId, Long emojiType) {
        return EmojiEntity.builder()
                .emojiType(emojiType)
                .boardId(BoardIdJpaVO.make(boardId.getId()))
                .emojiUser(EmojiUserEntity.make(UserIdJpaVO.make(userId.getId())))
                .build();
    }
}
