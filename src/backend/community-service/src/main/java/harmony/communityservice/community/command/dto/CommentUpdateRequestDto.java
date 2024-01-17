package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private Long userId;
    private Long commentId;
    private String comment;
}
