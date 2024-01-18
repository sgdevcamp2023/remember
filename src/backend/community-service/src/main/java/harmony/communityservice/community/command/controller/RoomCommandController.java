package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.RoomRegistrationRequestDto;
import harmony.communityservice.community.command.service.RoomCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
    public BaseResponse<?> registration(
            @RequestPart(value = "requestDto") @Validated RoomRegistrationRequestDto requestDto,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {

        roomCommandService.save(requestDto, profile);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
