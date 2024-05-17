package harmony.communityservice.guild.category.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RegisterCategoryController {

    private final RegisterCategoryUseCase registerCategoryUseCase;

    @PostMapping("/register/category")
    public BaseResponse<?> register(@RequestBody @Validated RegisterCategoryRequest registerCategoryRequest) {
        RegisterCategoryCommand registerCategoryCommand = getRegisterCategoryCommand(registerCategoryRequest);
        registerCategoryUseCase.register(registerCategoryCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private RegisterCategoryCommand getRegisterCategoryCommand(RegisterCategoryRequest registerCategoryRequest) {
        return new RegisterCategoryCommand(registerCategoryRequest.name(),
                registerCategoryRequest.userId(),
                registerCategoryRequest.guildId());
    }
}
