package harmony.communityservice.community.command.dto;

public record RegisterEmojiRequest(Long boardId,
                                   Long emojiType,
                                   Long userId) {

}
