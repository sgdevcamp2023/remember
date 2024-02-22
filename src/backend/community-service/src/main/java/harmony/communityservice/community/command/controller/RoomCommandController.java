package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.RoomDeleteRequestDto;
import harmony.communityservice.community.command.dto.RoomRegistrationRequestDto;
import harmony.communityservice.community.command.service.RoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomCommandController {

    private final RoomCommandService roomCommandService;

    @PostMapping("/registration/room")
    public BaseResponse<?> registration(@RequestBody @Validated RoomRegistrationRequestDto requestDto) {

        roomCommandService.save(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }


    @DeleteMapping("/delete/room")
    public BaseResponse<?> remove(@RequestBody @Validated RoomDeleteRequestDto requestDto) {
        roomCommandService.delete(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
