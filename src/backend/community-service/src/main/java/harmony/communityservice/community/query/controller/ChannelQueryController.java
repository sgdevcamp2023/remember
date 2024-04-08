package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.SearchChannelResponse;
import harmony.communityservice.community.query.service.ChannelQueryService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ChannelQueryController {

    private final ChannelQueryService channelQueryService;

    @GetMapping("/search/channel/list/{guildId}/{userId}")
    public BaseResponse<?> searchList(@PathVariable Long guildId, @PathVariable Long userId) {
        Map<Long, SearchChannelResponse> searchChannelResponseMap = channelQueryService.searchMapByGuildId(guildId,
                userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchChannelResponseMap);
    }
}
