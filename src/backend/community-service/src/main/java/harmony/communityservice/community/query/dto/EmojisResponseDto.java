package harmony.communityservice.community.query.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class EmojisResponseDto {
    private List<EmojiResponseDto> emojiResponseDtos;

    public EmojisResponseDto(List<EmojiResponseDto> emojiResponseDtos) {
        this.emojiResponseDtos = emojiResponseDtos;
    }
}
