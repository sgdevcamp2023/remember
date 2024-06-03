package harmony.communityservice.common.dto;

import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class LoadUserStateInGuildAndChannelFeignResponse {
    Map<Long, String> connectionStates;
    Map<Long, Set<Long>> channelStates;

    public LoadUserStateInGuildAndChannelFeignResponse(Map<Long, Set<Long>> channelStates,
                                                       Map<Long, String> connectionStates) {
        this.channelStates = channelStates;
        this.connectionStates = connectionStates;
    }
}
