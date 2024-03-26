package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ModifyBoardRequest {

    @NotNull
    private Long userId;
    @NotNull
    private Long boardId;

    private String title;
    private String content;
}
