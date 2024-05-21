package harmony.communityservice.common.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchUserStatusInDmRoomRequest {
    private List<Long> userIds = new ArrayList<>();

    public SearchUserStatusInDmRoomRequest(List<Long> userIds) {
        this.userIds = userIds;
    }
}
