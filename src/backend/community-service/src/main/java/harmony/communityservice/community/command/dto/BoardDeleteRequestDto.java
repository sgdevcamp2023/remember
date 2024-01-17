package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class BoardDeleteRequestDto {

    private Long boardId;
    private Long userId;
}
