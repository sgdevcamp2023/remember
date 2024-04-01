package harmony.communityservice.common.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public class SearchDmUserStateFeignResponse {

    private Map<Long, String> connectionStates;
}
