package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.dto.SearchGuildInvitationCodeRequest;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.GuildReadQueryService;
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
}
