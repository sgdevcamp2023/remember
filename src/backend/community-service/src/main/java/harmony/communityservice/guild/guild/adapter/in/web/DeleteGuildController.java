package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.DeleteGuildUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class DeleteGuildController {

    private final DeleteGuildUseCase deleteGuildUseCase;

    @DeleteMapping("/delete/guild")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteGuildRequest deleteGuildRequest) {
        DeleteGuildCommand deleteGuildCommand = new DeleteGuildCommand(deleteGuildRequest.managerId(),
                deleteGuildRequest.guildId());
        deleteGuildUseCase.delete(deleteGuildCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
