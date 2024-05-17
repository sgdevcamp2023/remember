package harmony.communityservice.guild.channel.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsCommand;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelsQuery;
import harmony.communityservice.guild.channel.application.port.in.LoadChannelResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadChannelsController {

    private final LoadChannelsQuery loadChannelsQuery;

    @GetMapping("/search/channel/list/{guildId}/{userId}")
    public BaseResponse<?> searchList(@PathVariable Long userId, @PathVariable Long guildId) {
        Map<Long, LoadChannelResponse> searchChannelResponseMap = loadChannelsQuery.loadChannels(
                new LoadChannelsCommand(guildId, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchChannelResponseMap);
    }
}
