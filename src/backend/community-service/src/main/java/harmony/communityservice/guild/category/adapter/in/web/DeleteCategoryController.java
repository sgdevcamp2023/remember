package harmony.communityservice.guild.category.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryCommand;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class DeleteCategoryController {

    private final DeleteCategoryUseCase deleteCategoryUseCase;

    @DeleteMapping("/delete/category")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteCategoryRequest deleteCategoryRequest) {
        DeleteCategoryCommand deleteCategoryCommand = getDeleteCategoryCommand(deleteCategoryRequest);
        deleteCategoryUseCase.delete(deleteCategoryCommand);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    private DeleteCategoryCommand getDeleteCategoryCommand(DeleteCategoryRequest deleteCategoryRequest) {
        return new DeleteCategoryCommand(deleteCategoryRequest.guildId(),
                deleteCategoryRequest.categoryId(),
                deleteCategoryRequest.userId());
    }

}
