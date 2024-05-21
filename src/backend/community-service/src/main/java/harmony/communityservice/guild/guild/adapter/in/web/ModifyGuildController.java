package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameCommand;
import harmony.communityservice.guild.guild.application.port.in.ModifyGuildNicknameUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyGuildController {

    private final ModifyGuildNicknameUseCase modifyGuildNicknameUseCase;

    @PatchMapping("/modify/guild/username")
    public BaseResponse<?> modifyNicknameInGuild(
            @RequestBody @Validated ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        ModifyGuildNicknameCommand modifyGuildNicknameCommand = makeModifyGuildNicknameCommand(
                modifyUserNicknameInGuildRequest);
        modifyGuildNicknameUseCase.modifyNickname(modifyGuildNicknameCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private ModifyGuildNicknameCommand makeModifyGuildNicknameCommand(
            ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        return new ModifyGuildNicknameCommand(
                modifyUserNicknameInGuildRequest.guildId(),
                modifyUserNicknameInGuildRequest.userId(),
                modifyUserNicknameInGuildRequest.nickname());
    }
}
