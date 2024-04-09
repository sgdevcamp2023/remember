package harmony.communityservice.user.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.user.dto.ModifyUserNicknameRequest;
import harmony.communityservice.user.dto.ModifyUserProfileRequest;
import harmony.communityservice.user.dto.RegisterUserRequest;
import harmony.communityservice.user.service.command.UserCommandService;
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
    public BaseResponse<?> register(@RequestBody @Validated RegisterUserRequest registerUserRequest) {
        userCommandService.register(registerUserRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/user/profile")
    public BaseResponse<?> modifyProfile(@RequestBody @Validated ModifyUserProfileRequest modifyUserProfileRequest) {
        userCommandService.modifyProfile(modifyUserProfileRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/user/nickname")
    public BaseResponse<?> modifyNickname(@RequestBody @Validated ModifyUserNicknameRequest modifyUserNicknameRequest) {
        userCommandService.modifyNickname(modifyUserNicknameRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
