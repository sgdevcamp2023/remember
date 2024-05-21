package harmony.communityservice.guild.channel.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelCommand;
import harmony.communityservice.guild.channel.application.port.in.DeleteChannelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class DeleteChannelController {

    private final DeleteChannelUseCase deleteChannelUseCase;

    @DeleteMapping("/delete/channel")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteChannelRequest deleteChannelRequest) {
        DeleteChannelCommand deleteChannelCommand = getDeleteChannelCommand(deleteChannelRequest);
        deleteChannelUseCase.delete(deleteChannelCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private DeleteChannelCommand getDeleteChannelCommand(DeleteChannelRequest deleteChannelRequest) {
        return DeleteChannelCommand.builder()
                .type(deleteChannelRequest.type())
                .guildId(deleteChannelRequest.guildId())
                .channelId(deleteChannelRequest.channelId())
                .userId(deleteChannelRequest.userId())
                .build();
    }
}
