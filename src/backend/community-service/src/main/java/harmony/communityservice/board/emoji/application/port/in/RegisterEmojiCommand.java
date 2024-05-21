package harmony.communityservice.board.emoji.application.port.in;

public record RegisterEmojiCommand(Long boardId,
                                   Long emojiType,
                                   Long userId) {
}
