package harmony.communityservice.common.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public class LoadDmUserStateFeignResponse {

    private Map<Long, String> connectionStates;

    public LoadDmUserStateFeignResponse(Map<Long, String> connectionStates) {
        this.connectionStates = connectionStates;
    }
}
