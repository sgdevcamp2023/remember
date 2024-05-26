package harmony.communityservice.board.emoji.application.port.in;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LoadEmojisResponse {
    private List<LoadEmojiResponse> searchEmojiResponses = new ArrayList<>();

    public LoadEmojisResponse(List<LoadEmojiResponse> searchEmojiResponses) {
        this.searchEmojiResponses = searchEmojiResponses;
    }
}
