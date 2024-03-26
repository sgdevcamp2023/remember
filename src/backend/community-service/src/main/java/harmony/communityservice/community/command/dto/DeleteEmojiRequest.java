package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteEmojiRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long emojiId;
}
