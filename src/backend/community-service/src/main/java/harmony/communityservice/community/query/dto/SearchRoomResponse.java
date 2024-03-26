package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchRoomResponse {

    private Long roomId;
    private String name;
    private String profile;

    @Builder
    public SearchRoomResponse(Long roomId, String name, String profile) {
        this.roomId = roomId;
        this.name = name;
        this.profile = profile;
    }
}
