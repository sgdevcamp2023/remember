package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.command.dto.DeleteGuildRequest;
import harmony.communityservice.community.command.dto.RegisterGuildRequest;
import harmony.communityservice.community.command.dto.ModifyUserNicknameInGuildRequest;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.domain.GuildRead;
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
            @RequestPart(value = "requestDto") @Validated RegisterGuildRequest requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {
        GuildRead guildRead = guildCommandService.register(requestDto, profile);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", guildRead);
    }

    @GetMapping("/join/guild/{invitationCode}/{userId}")
    public BaseResponse<?> joinByInvitationCode(@PathVariable String invitationCode, @PathVariable Long userId) {
        guildCommandService.joinByInvitationCode(invitationCode, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/guild")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteGuildRequest requestDto) {
        guildCommandService.delete(requestDto);
        producerService.publishGuildDeletionEvent(requestDto.getGuildId());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/guild/username")
    public BaseResponse<?> modifyNicknameInGuild(@RequestBody @Validated ModifyUserNicknameInGuildRequest requestDto) {
        guildCommandService.modifyUserNicknameInGuild(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
