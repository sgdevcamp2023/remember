package harmony.communityservice.guild.guild.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.guild.dto.SearchUserStatesInGuildResponse;
import harmony.communityservice.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.dto.SearchGuildInvitationCodeRequest;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import harmony.communityservice.guild.guild.service.query.GuildReadQueryService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class GuildQueryController {

    private final GuildQueryService guildQueryService;
    private final GuildReadQueryService guildReadQueryService;

    @PostMapping("/search/invitation/code/guild")
    public BaseResponse<?> searchInvitationCode(
            @RequestBody @Validated SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest) {
        String invitationCode = guildQueryService.searchInvitationCode(searchGuildInvitationCodeRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", invitationCode);
    }

    @GetMapping("/search/guild/{userId}")
    public BaseResponse<?> searchBelongToUser(@PathVariable Long userId) {
        Map<Long, GuildRead> guildReads = guildReadQueryService.searchMapByUserId(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", guildReads);
    }

    @GetMapping("/search/user/status/guild/{guildId}/{userId}")
    public BaseResponse<?> searchUserStatusInGuild(@PathVariable Long guildId, @PathVariable Long userId) {
        SearchUserStatesInGuildResponse searchUserStatesInGuildResponse = guildQueryService.searchUserStatesInGuild(
                new SearchParameterMapperRequest(guildId, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchUserStatesInGuildResponse);
    }
}
