package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UserStatusRequestDto {
    private Long guildId;
    private List<Long> userIds = new ArrayList<>();

    public UserStatusRequestDto(Long guildId, List<Long> userIds) {
        this.guildId = guildId;
        this.userIds = userIds;
    }
}
