package harmony.communityservice.guild.channel.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.RegisterChannelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RegisterChannelController {

    private final RegisterChannelUseCase registerChannelUseCase;

    @PostMapping({"/register/guild/channel", "/register/category/channel"})
    public BaseResponse<?> registerChannel(
            @RequestBody @Validated RegisterChannelRequest registerChannelRequest) {
        RegisterChannelCommand registerChannelCommand = getRegisterChannelCommand(registerChannelRequest);
        registerChannelUseCase.register(registerChannelCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterChannelCommand getRegisterChannelCommand(RegisterChannelRequest registerChannelRequest) {
        return RegisterChannelCommand.builder()
                .type(registerChannelRequest.type())
                .name(registerChannelRequest.name())
                .userId(registerChannelRequest.userId())
                .categoryId(registerChannelRequest.categoryId())
                .guildId(registerChannelRequest.guildId())
                .build();
    }
}
