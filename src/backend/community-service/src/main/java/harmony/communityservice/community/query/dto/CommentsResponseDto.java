package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentsResponseDto {
    private List<CommentResponseDto> commentResponseDto = new ArrayList<>();

    @Builder
    public CommentsResponseDto(List<CommentResponseDto> commentResponseDto) {
        this.commentResponseDto = commentResponseDto;
    }
}
