package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesQuery;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildUserStatesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadGuildUserStatesController {

    private final LoadGuildUserStatesQuery loadGuildUserStatesQuery;

    @GetMapping("/search/user/status/guild/{guildId}/{userId}")
    public BaseResponse<?> searchUserStatusInGuild(@PathVariable Long userId, @PathVariable Long guildId) {
        LoadGuildUserStatesCommand loadGuildUserStatesCommand = new LoadGuildUserStatesCommand(userId, guildId);
        LoadGuildUserStatesResponse searchUserStatesInGuildResponse = loadGuildUserStatesQuery.load
                (loadGuildUserStatesCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchUserStatesInGuildResponse);
    }
}
