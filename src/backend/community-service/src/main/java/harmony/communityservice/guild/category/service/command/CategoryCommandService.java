package harmony.communityservice.guild.category.service.command;

import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;

public interface CategoryCommandService {

    void registerCategory(RegisterCategoryRequest registerCategoryRequest);

    void delete(DeleteCategoryRequest deleteCategoryRequest);

    void modify(ModifyCategoryRequest modifyCategoryRequest);
}
