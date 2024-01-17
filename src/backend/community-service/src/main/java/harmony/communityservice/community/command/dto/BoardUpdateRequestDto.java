package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class BoardUpdateRequestDto {

    private Long userId;
    private Long boardId;
    private String title;
    private String content;
}
