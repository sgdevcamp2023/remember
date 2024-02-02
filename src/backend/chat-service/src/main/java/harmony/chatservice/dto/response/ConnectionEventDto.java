package harmony.chatservice.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionEventDto {

    private Long userId;
    private Long guildId;
    private Long channelId;
    private String type;
    private String state;
    private List<Long> guildIds;
    private List<Long> roomIds;

    @Builder
    public ConnectionEventDto(Long userId, Long guildId, Long channelId, String type, String state, List<Long> guildIds,
                              List<Long> roomIds) {
        this.userId = userId;
        this.guildId = guildId;
        this.channelId = channelId;
        this.type = type;
        this.state = state;
        this.guildIds = guildIds;
        this.roomIds = roomIds;
    }
}