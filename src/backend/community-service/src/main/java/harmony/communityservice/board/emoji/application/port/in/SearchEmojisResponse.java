package harmony.communityservice.board.emoji.application.port.in;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchEmojisResponse {
    private List<SearchEmojiResponse> searchEmojiResponses = new ArrayList<>();

    public SearchEmojisResponse(List<SearchEmojiResponse> searchEmojiResponses) {
        this.searchEmojiResponses = searchEmojiResponses;
    }
}
