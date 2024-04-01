package harmony.communityservice.community.query.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SearchRoomsAndGuildsResponse {

    private List<Long> roomIds;
    private List<Long> guildIds;

    public SearchRoomsAndGuildsResponse(List<Long> roomIds, List<Long> guildIds) {
        this.roomIds = roomIds;
        this.guildIds = guildIds;
    }
}
