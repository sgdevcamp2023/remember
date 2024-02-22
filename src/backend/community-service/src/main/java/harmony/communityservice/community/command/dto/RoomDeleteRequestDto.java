package harmony.communityservice.community.command.dto;

import lombok.Getter;

@Getter
public class RoomDeleteRequestDto {
    private Long firstUser;
    private Long secondUser;
}
