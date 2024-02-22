package harmony.communityservice.common.dto;

import java.util.Map;
import lombok.Getter;

@Getter
public class DmUserStateFeignResponseDto {

    private Map<Long, String> connectionStates;
}
