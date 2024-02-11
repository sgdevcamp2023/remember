package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class DmUserStatesRequestDto {
    private List<Long> userIds = new ArrayList<>();

    public DmUserStatesRequestDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
