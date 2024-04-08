package harmony.communityservice.board.comment.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCommentsResponse {
    private List<SearchCommentResponse> searchCommentResponses = new ArrayList<>();

    @Builder
    public SearchCommentsResponse(List<SearchCommentResponse> searchCommentResponses) {
        this.searchCommentResponses = searchCommentResponses;
    }
}
