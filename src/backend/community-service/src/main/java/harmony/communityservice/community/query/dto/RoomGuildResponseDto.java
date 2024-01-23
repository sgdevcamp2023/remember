package harmony.communityservice.community.query.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class RoomGuildResponseDto {

    private List<Long> roomIds;
    private List<Long> guildIds;

    public RoomGuildResponseDto(List<Long> roomIds, List<Long> guildIds) {
        this.roomIds = roomIds;
        this.guildIds = guildIds;
    }
}
