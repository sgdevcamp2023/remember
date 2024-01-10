package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.UserStoreRequestDto;
import harmony.communityservice.community.command.service.UserCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserController {

    private final UserCommandService userCommandService;

    @PostMapping("/registration/user")
    public BaseResponse<?> join(@RequestBody @Validated UserStoreRequestDto userStoreRequestDto) {
        userCommandService.save(userStoreRequestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

}
