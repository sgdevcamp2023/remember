package harmony.communityservice.common.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class LoadRoomsAndGuildsResponse {

    private List<Long> roomIds;
    private List<Long> guildIds;

    public LoadRoomsAndGuildsResponse(List<Long> roomIds, List<Long> guildIds) {
        this.roomIds = roomIds;
        this.guildIds = guildIds;
    }
}
