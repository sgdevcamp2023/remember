package harmony.communityservice.common.dto;

import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SearchUserStateInGuildAndRoomFeignResponse {
    Map<Long, String> connectionStates;
    Map<Long, Set<Long>> channelStates;
}
