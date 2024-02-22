package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class EmojisResponseDto {
    private List<EmojiResponseDto> emojiResponseDtos = new ArrayList<>();

    public EmojisResponseDto(List<EmojiResponseDto> emojiResponseDtos) {
        this.emojiResponseDtos = emojiResponseDtos;
    }
}
