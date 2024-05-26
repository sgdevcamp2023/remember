package harmony.communityservice.board.emoji.domain;

import lombok.Getter;

@Getter
enum EmojiTypeRange {

    MIN(1L), MAX(256L);

    private final Long value;

    EmojiTypeRange(Long value) {
        this.value = value;
    }
}
