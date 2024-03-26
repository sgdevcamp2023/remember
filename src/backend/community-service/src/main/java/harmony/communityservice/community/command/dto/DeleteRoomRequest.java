package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class DeleteRoomRequest {
    private Long firstUser;
    private Long secondUser;
}
