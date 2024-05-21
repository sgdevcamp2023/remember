package harmony.communityservice.guild.guild.adapter.in.web;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchUserStatesInGuildRequest {
    private Long guildId;
    private List<Long> userIds = new ArrayList<>();

    public SearchUserStatesInGuildRequest(Long guildId, List<Long> userIds) {
        this.guildId = guildId;
        this.userIds = userIds;
    }
}
