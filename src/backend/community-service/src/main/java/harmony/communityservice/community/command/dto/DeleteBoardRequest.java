package harmony.communityservice.community.command.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteBoardRequest {

    @NotNull
    private Long boardId;
    @NotNull
    private Long userId;
}
