package harmony.communityservice.common.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SearchUserStatusInDmRoomRequest {
    private List<Long> userIds = new ArrayList<>();

    public SearchUserStatusInDmRoomRequest(List<Long> userIds) {
        this.userIds = userIds;
    }
}
