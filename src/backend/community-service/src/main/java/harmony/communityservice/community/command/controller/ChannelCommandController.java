package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.command.dto.DeleteChannelRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import harmony.communityservice.community.command.service.ChannelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ChannelCommandController {

    private final ChannelCommandService channelCommandService;
    private final ProducerService producerService;

    @PostMapping("/register/category/channel")
    public BaseResponse<?> registerChannelInCategory(
            @RequestBody @Validated RegisterChannelRequest registerChannelRequest) {
        producerService.publishChannelCreationEvent(registerChannelRequest.guildId(),
                registerChannelRequest.categoryId(), channelCommandService.register(registerChannelRequest),
                registerChannelRequest.name(), registerChannelRequest.type());
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
        producerService.publishChannelDeletionEvent(deleteChannelRequest.channelId());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
