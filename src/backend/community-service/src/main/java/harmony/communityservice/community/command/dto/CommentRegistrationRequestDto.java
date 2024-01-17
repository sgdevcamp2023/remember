package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class CommentRegistrationRequestDto {
    private Long userId;
    private Long boardId;
    private String comment;
    private String writerName;
    private String writerProfile;
}
