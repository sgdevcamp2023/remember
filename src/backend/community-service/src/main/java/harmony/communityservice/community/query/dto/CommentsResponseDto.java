package harmony.communityservice.community.query.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentsResponseDto {
    List<CommentResponseDto> commentResponseDto;

    @Builder
    public CommentsResponseDto(List<CommentResponseDto> commentResponseDto) {
        this.commentResponseDto = commentResponseDto;
    }
}
