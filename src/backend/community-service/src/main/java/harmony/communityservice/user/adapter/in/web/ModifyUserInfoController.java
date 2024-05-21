package harmony.communityservice.user.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.user.application.port.in.ModifyUserInfoUseCase;
import harmony.communityservice.user.application.port.in.ModifyUserNicknameCommand;
import harmony.communityservice.user.application.port.in.ModifyUserProfileCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyUserInfoController {

    private final ModifyUserInfoUseCase modifyUserInfoUseCase;

    @PatchMapping("/modify/user/profile")
    public BaseResponse<?> modifyProfile(@RequestBody @Validated ModifyUserProfileRequest modifyUserProfileRequest) {
        ModifyUserProfileCommand modifyUserProfileCommand = new ModifyUserProfileCommand(
                modifyUserProfileRequest.userId(), modifyUserProfileRequest.profile());
        modifyUserInfoUseCase.modifyProfile(modifyUserProfileCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/user/nickname")
    public BaseResponse<?> modifyNickname(@RequestBody @Validated ModifyUserNicknameRequest modifyUserNicknameRequest) {
        ModifyUserNicknameCommand modifyUserNicknameCommand = new ModifyUserNicknameCommand(
                modifyUserNicknameRequest.userId(), modifyUserNicknameRequest.nickname());
        modifyUserInfoUseCase.modifyNickname(modifyUserNicknameCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
