package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UserStatusRequestDto {
    private List<Long> userIds = new ArrayList<>();

    public UserStatusRequestDto(List<Long> userIds) {
        this.userIds = userIds;
    }
}
