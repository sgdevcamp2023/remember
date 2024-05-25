package harmony.communityservice.guild.guild.adapter.in.web;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LoadUserStatesInGuildRequest {
    private Long guildId;
    private List<Long> userIds = new ArrayList<>();

    public LoadUserStatesInGuildRequest(Long guildId, List<Long> userIds) {
        this.guildId = guildId;
        this.userIds = userIds;
    }
}
