package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.InvitationRequestDto;
import harmony.communityservice.community.domain.GuildRead;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.GuildReadQueryService;
import java.util.List;
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

    @PostMapping("/invitation/guild")
    public BaseResponse<?> invitation(@RequestBody @Validated InvitationRequestDto requestDto) {
        String code = guildQueryService.findInviteCode(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", code);
    }

    @GetMapping("/check/guild/{userId}")
    public BaseResponse<?> checkGuild(@PathVariable Long userId) {
        List<GuildRead> findGuildReads = guildReadQueryService.findGuildReadsByUserId(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", findGuildReads);
    }
}
