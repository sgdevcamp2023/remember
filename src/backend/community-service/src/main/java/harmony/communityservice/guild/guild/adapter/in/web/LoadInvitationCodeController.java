package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeCommand;
import harmony.communityservice.guild.guild.application.port.in.LoadInvitationCodeQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadInvitationCodeController {

    private final LoadInvitationCodeQuery loadInvitationCodeQuery;

    @PostMapping("/search/invitation/code/guild")
    public BaseResponse<?> searchInvitationCode(
            @RequestBody @Validated SearchGuildInvitationCodeRequest searchGuildInvitationCodeRequest) {
        LoadInvitationCodeCommand loadInvitationCodeCommand = new LoadInvitationCodeCommand(
                searchGuildInvitationCodeRequest.userId(), searchGuildInvitationCodeRequest.guildId());
        String invitationCode = loadInvitationCodeQuery.load(loadInvitationCodeCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", invitationCode);
    }
}
