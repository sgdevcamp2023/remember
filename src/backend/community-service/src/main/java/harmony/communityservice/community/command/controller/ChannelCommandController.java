package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.community.command.dto.ChannelDeleteRequestDto;
import harmony.communityservice.community.command.dto.ChannelRegistrationRequestDto;
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

    @PostMapping("/registration/category/channel")
    public BaseResponse<?> registration(@RequestBody @Validated ChannelRegistrationRequestDto requestDto) {
        Long channelId = channelCommandService.registration(requestDto);
        producerService.sendCreateChannel(requestDto.getGuildId(), requestDto.getCategoryId(), channelId,
                requestDto.getName(), requestDto.getType());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PostMapping("/registration/guild/channel")
    public BaseResponse<?> guildRegistration(@RequestBody @Validated ChannelRegistrationRequestDto requestDto) {
        channelCommandService.registration(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/channel")
    public BaseResponse<?> deleteChannel(@RequestBody @Validated ChannelDeleteRequestDto requestDto) {
        channelCommandService.remove(requestDto);
        producerService.sendDeleteChannel(requestDto.getChannelId());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
