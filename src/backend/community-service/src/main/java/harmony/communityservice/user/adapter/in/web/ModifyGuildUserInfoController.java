package harmony.communityservice.user.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.adapter.in.web.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.user.service.command.UserReadCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyGuildUserInfoController {

    private final UserReadCommandService userReadCommandService;

    @PatchMapping("/modify/guild/username")
    public BaseResponse<?> modifyNicknameInGuild(
            @RequestBody @Validated ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        userReadCommandService.modifyUserNicknameInGuild(modifyUserNicknameInGuildRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
