package harmony.chatservice.dto.response;

import harmony.chatservice.dto.CommunityMessageDto;
import harmony.chatservice.dto.EmojiDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityCommentResponse {

    private CommunityMessageDto communityMessageDto;
    private List<EmojiDto> emojiDtos;

    public CommunityCommentResponse(CommunityMessageDto messageDto, List<EmojiDto> emojiDtos) {
        this.communityMessageDto = messageDto;
        this.emojiDtos = emojiDtos;
    }
}
