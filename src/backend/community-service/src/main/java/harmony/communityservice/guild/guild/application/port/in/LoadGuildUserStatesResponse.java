package harmony.communityservice.guild.guild.application.port.in;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class LoadGuildUserStatesResponse {
    private Map<Long, ?> guildStates = new HashMap<>();
    private Map<Long, Map<Long, ?>> voiceChannelStates = new HashMap<>();

    public LoadGuildUserStatesResponse(Map<Long, ?> guildStates, Map<Long, Map<Long, ?>> voiceChannelStates) {
        this.guildStates = guildStates;
        this.voiceChannelStates = voiceChannelStates;
    }
}
