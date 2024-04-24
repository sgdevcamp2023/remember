package harmony.communityservice.guild.channel.controller;

import harmony.communityservice.common.annotation.AuthorizeUser;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.channel.dto.SearchChannelResponse;
import harmony.communityservice.guild.channel.service.query.ChannelQueryService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthorizeUser
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ChannelQueryController {

    private final ChannelQueryService channelQueryService;

    @GetMapping("/search/channel/list/{guildId}/{userId}")
    public BaseResponse<?> searchList(@PathVariable Long userId, @PathVariable Long guildId) {
        Map<Integer, SearchChannelResponse> searchChannelResponseMap = channelQueryService.searchMapByGuildId(
                new SearchParameterMapperRequest(guildId, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchChannelResponseMap);
    }
}