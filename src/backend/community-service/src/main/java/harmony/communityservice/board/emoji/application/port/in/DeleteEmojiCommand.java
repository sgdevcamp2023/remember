package harmony.communityservice.board.emoji.application.port.in;

public record DeleteEmojiCommand(Long userId, Long emojiId) {
}
