package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.community.command.dto.GuildRegistrationRequestDto;
import harmony.communityservice.community.command.dto.InvitationRequestDto;
import harmony.communityservice.community.command.service.GuildCommandService;
import harmony.communityservice.community.query.service.GuildQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
public class GuildController {

    private final ContentService contentService;
    private final GuildCommandService guildCommandService;
    private final GuildQueryService guildQueryService;

    @PostMapping("/registration/guild")
    public BaseResponse<?> registration(
            @RequestPart(value = "requestDto") @Validated GuildRegistrationRequestDto requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {

        String imageAddr = contentService.image(profile);
        guildCommandService.save(requestDto, imageAddr);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PostMapping("/invitation/guild")
    public BaseResponse<?> invitation(@RequestBody @Validated InvitationRequestDto requestDto) {
        String code = guildQueryService.findInviteCode(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", code);
    }

    @GetMapping("/join/guild/{invitationCode}")
    public BaseResponse<?> join(@PathVariable String invitationCode) {
        guildCommandService.join(invitationCode);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
