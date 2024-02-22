package harmony.communityservice.community.query.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class UserStatesResponseDto {
    private Map<Long, ?> guildStates = new HashMap<>();
    private Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();

    public UserStatesResponseDto(Map<Long, ?> guildStates, Map<Long, Map<Long, ?>> voiceChannelStates) {
        this.guildStates = guildStates;
        this.voiceChannelStates = voiceChannelStates;
    }
}
