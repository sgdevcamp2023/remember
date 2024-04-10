package harmony.communityservice.guild.category.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.category.service.command.CategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CategoryCommandController {

    private final CategoryCommandService categoryCommandService;

    @PostMapping("/register/category")
    public BaseResponse<?> register(@RequestBody @Validated RegisterCategoryRequest registerCategoryRequest) {
        categoryCommandService.registerCategory(registerCategoryRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/category")
    public BaseResponse<?> delete(@RequestBody @Validated DeleteCategoryRequest deleteCategoryRequest) {
        categoryCommandService.delete(deleteCategoryRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @PatchMapping("/modify/category")
    public BaseResponse<?> modify(@RequestBody @Validated ModifyCategoryRequest modifyCategoryRequest) {
        categoryCommandService.modify(modifyCategoryRequest);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
