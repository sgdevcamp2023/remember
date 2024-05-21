package harmony.communityservice.guild.guild.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildCommand;
import harmony.communityservice.guild.guild.application.port.in.RegisterGuildUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RegisterGuildController {

    private final RegisterGuildUseCase registerGuildUseCase;

    @PostMapping("/register/guild")
    public BaseResponse<?> register(
            @RequestPart(value = "registerGuildRequest") @Validated RegisterGuildRequest registerGuildRequest,
            @RequestPart(name = "profile", required = false) MultipartFile profile) {
        RegisterGuildCommand registerGuildCommand = new RegisterGuildCommand(profile, registerGuildRequest.managerId(),
                registerGuildRequest.name());
        return new BaseResponse<>(HttpStatus.OK.value(), "OK",
                registerGuildUseCase.register(registerGuildCommand));
    }
}
