package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class EmojiRegistrationRequestDto {

    private Long boardId;
    private Long emojiType;
    private Long userId;
}
