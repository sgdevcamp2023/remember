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
public class CommunityMessageResponse {

    private CommunityMessageDto communityMessageDto;
    private List<EmojiDto> emojis;

    public CommunityMessageResponse(CommunityMessageDto messageDto, List<EmojiDto> emojis) {
        this.communityMessageDto = messageDto;
        this.emojis = emojis;
    }
}