package harmony.communityservice.guild.guild.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class SearchUserStatesInGuildResponse {
    private Map<Long, ?> guildStates = new HashMap<>();
    private Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();

    public SearchUserStatesInGuildResponse(Map<Long, ?> guildStates, Map<Long, Map<Long, ?>> voiceChannelStates) {
        this.guildStates = guildStates;
        this.voiceChannelStates = voiceChannelStates;
    }
}
