package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchEmojisResponse {
    private List<SearchEmojiResponse> searchEmojiResponses = new ArrayList<>();

    public SearchEmojisResponse(List<SearchEmojiResponse> emojiResponseDtos) {
        this.searchEmojiResponses = emojiResponseDtos;
    }
}
