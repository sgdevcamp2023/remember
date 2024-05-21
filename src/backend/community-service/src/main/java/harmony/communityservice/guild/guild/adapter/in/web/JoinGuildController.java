package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.JoinGuildUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class JoinGuildController {

    private final JoinGuildUseCase joinGuildUseCase;

    @GetMapping("/join/guild/{invitationCode}/{userId}")
    public BaseResponse<?> joinByInvitationCode(@PathVariable Long userId, @PathVariable String invitationCode) {
        JoinGuildCommand joinGuildCommand = new JoinGuildCommand(userId, invitationCode);
        joinGuildUseCase.join(joinGuildCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
