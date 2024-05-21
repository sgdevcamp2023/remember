package harmony.communityservice.user.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.user.application.port.in.RegisterUserCommand;
import harmony.communityservice.user.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserRegisterController {

    private final RegisterUserUseCase registerUserUseCase;

    @PostMapping("/register/user")
    public BaseResponse<?> register(@RequestBody @Validated RegisterUserRequest registerUserRequest) {
        RegisterUserCommand registerUserCommand = getRegisterUserCommand(registerUserRequest);
        registerUserUseCase.register(registerUserCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterUserCommand getRegisterUserCommand(RegisterUserRequest registerUserRequest) {
        return new RegisterUserCommand(registerUserRequest.userId(),
                registerUserRequest.email(), registerUserRequest.name(),
                registerUserRequest.profile());
    }
}
