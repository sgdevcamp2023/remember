package harmony.communityservice.board.comment.application.port.in;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LoadCommentsResponse {
    private List<LordCommentResponse> searchCommentResponses = new ArrayList<>();

    @Builder
    public LoadCommentsResponse(List<LordCommentResponse> searchCommentResponses) {
        this.searchCommentResponses = searchCommentResponses;
    }
}
