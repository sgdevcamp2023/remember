package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.GuildDeleteRequestDto;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.GuildUpdateNicknameRequestDto;
import harmony.communityservice.community.command.service.GuildCommandService;
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

    @PostMapping("/registration/guild")
    public BaseResponse<?> registration(
            @RequestPart(value = "requestDto") @Validated GuildRegistrationRequestDto requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {
        guildCommandService.save(requestDto, profile);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }


    @GetMapping("/join/guild/{invitationCode}/{userId}")
    public BaseResponse<?> join(@PathVariable String invitationCode, @PathVariable Long userId) {
        guildCommandService.join(invitationCode, userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/guild")
    public BaseResponse<?> delete(@RequestBody @Validated GuildDeleteRequestDto requestDto) {
        guildCommandService.remove(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/change/guild/username")
    public BaseResponse<?> updateGuildNickname(@RequestBody @Validated GuildUpdateNicknameRequestDto requestDto) {
        guildCommandService.updateGuildNickname(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
