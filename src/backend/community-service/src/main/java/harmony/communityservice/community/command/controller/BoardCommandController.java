package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.DeleteBoardRequest;
import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.command.dto.ModifyBoardRequest;
import harmony.communityservice.community.command.service.BoardCommandService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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

    @PostMapping("/register/board")
    public BaseResponse<?> register(
            @RequestPart(value = "requestDto") RegisterBoardRequest registerBoardRequest,
            @RequestPart(name = "images", required = false) List<MultipartFile> images) {
        boardCommandService.register(registerBoardRequest, images);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/update/board")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyBoardRequest modifyBoardRequest) {
        boardCommandService.modify(modifyBoardRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/board")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteBoardRequest deleteBoardRequest) {
        boardCommandService.delete(deleteBoardRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}


