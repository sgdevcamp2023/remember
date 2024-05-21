package harmony.communityservice.guild.category.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.ModifyCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class ModifyCategoryController {

    private final ModifyCategoryUseCase modifyCategoryUseCase;

    @PatchMapping("/modify/category")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyCategoryRequest modifyCategoryRequest) {
        ModifyCategoryCommand modifyCategoryCommand = getModifyCategoryCommand(modifyCategoryRequest);
        modifyCategoryUseCase.modify(modifyCategoryCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private ModifyCategoryCommand getModifyCategoryCommand(ModifyCategoryRequest modifyCategoryRequest) {
        return new ModifyCategoryCommand(modifyCategoryRequest.guildId(),
                modifyCategoryRequest.userId(),
                modifyCategoryRequest.categoryId(), modifyCategoryRequest.name());
    }
}
