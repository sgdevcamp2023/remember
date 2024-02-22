package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomResponseDto {

    private Long roomId;
    private String name;
    private String profile;

    @Builder
    public RoomResponseDto(Long roomId, String name, String profile) {
        this.roomId = roomId;
        this.name = name;
        this.profile = profile;
    }
}
