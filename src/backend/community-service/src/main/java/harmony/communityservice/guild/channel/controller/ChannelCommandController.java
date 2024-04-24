package harmony.communityservice.guild.channel.controller;

import harmony.communityservice.common.annotation.AuthorizeUser;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.channel.dto.DeleteChannelRequest;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthorizeUser
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ChannelCommandController {

    private final ChannelCommandService channelCommandService;

    @PostMapping("/register/category/channel")
    public BaseResponse<?> registerChannelInCategory(
            @RequestBody @Validated RegisterChannelRequest registerChannelRequest) {
        channelCommandService.register(registerChannelRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PostMapping("/register/guild/channel")
    public BaseResponse<?> registerChannelInGuild(
            @RequestBody @Validated RegisterChannelRequest registerChannelRequest) {
        channelCommandService.register(registerChannelRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/channel")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteChannelRequest deleteChannelRequest) {
        channelCommandService.delete(deleteChannelRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}