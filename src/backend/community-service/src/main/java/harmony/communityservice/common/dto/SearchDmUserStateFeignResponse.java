package harmony.communityservice.common.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public class SearchDmUserStateFeignResponse {

    private Map<Long, String> connectionStates;

    public SearchDmUserStateFeignResponse(Map<Long, String> connectionStates) {
        this.connectionStates = connectionStates;
    }
}
