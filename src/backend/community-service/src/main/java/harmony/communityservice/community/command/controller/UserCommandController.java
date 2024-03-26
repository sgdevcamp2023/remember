package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.ModifyUserNicknameRequest;
import harmony.communityservice.community.command.dto.ModifyUserProfileRequest;
import harmony.communityservice.community.command.dto.RegisterUserRequest;
import harmony.communityservice.community.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/register/user")
    public BaseResponse<?> register(@RequestBody @Validated RegisterUserRequest userStoreRequestDto) {
        userCommandService.register(userStoreRequestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/user/profile")
    public BaseResponse<?> modifyProfile(@RequestBody @Validated ModifyUserProfileRequest requestDto) {
        userCommandService.modifyProfile(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/user/nickname")
    public BaseResponse<?> modifyNickname(@RequestBody @Validated ModifyUserNicknameRequest requestDto) {
        userCommandService.modifyNickname(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
