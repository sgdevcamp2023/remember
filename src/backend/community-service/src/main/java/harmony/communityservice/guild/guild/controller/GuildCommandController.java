package harmony.communityservice.guild.guild.controller;

import harmony.communityservice.guild.guild.dto.DeleteGuildRequest;
import harmony.communityservice.guild.guild.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.guild.guild.dto.RegisterGuildRequest;
import harmony.communityservice.guild.guild.service.command.GuildCommandService;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.guild.dto.RegisterUserUsingInvitationCodeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class GuildCommandController {
    private final GuildCommandService guildCommandService;
    private final ProducerService producerService;

    @PostMapping("/register/guild")
    public BaseResponse<?> register(
            @RequestPart(value = "registerGuildRequest") @Validated RegisterGuildRequest registerGuildRequest,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {
        return new BaseResponse<>(HttpStatus.OK.value(), "OK",
                guildCommandService.register(registerGuildRequest, profile));
    }

    @GetMapping("/join/guild/{invitationCode}/{userId}")
    public BaseResponse<?> joinByInvitationCode(@PathVariable String invitationCode, @PathVariable Long userId) {
        guildCommandService.joinByInvitationCode(new RegisterUserUsingInvitationCodeRequest(invitationCode, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/guild")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteGuildRequest deleteGuildRequest) {
        guildCommandService.delete(deleteGuildRequest);
        producerService.publishGuildDeletionEvent(deleteGuildRequest.guildId());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/guild/username")
    public BaseResponse<?> modifyNicknameInGuild(
            @RequestBody @Validated ModifyUserNicknameInGuildRequest modifyUserNicknameInGuildRequest) {
        guildCommandService.modifyUserNicknameInGuild(modifyUserNicknameInGuildRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
