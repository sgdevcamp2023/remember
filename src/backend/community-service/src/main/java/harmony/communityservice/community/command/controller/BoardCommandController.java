package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.BoardDeleteRequestDto;
import harmony.communityservice.community.command.dto.BoardRegistrationRequestDto;
import harmony.communityservice.community.command.dto.BoardUpdateRequestDto;
import harmony.communityservice.community.command.service.BoardCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class BoardCommandController {

    private final BoardCommandService boardCommandService;

    @PostMapping("/registration/board")
    public BaseResponse<?> registration(
            @RequestPart(value = "boardRegistrationDto") BoardRegistrationRequestDto boardRegistrationDto,
            @RequestPart(name = "images", required = false) List<MultipartFile> images) {

        boardCommandService.save(boardRegistrationDto, images);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/change/board")
    public BaseResponse<?> change(@RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
        boardCommandService.update(boardUpdateRequestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/board")
    public BaseResponse<?> delete(@RequestBody BoardDeleteRequestDto requestDto) {
        boardCommandService.delete(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}


