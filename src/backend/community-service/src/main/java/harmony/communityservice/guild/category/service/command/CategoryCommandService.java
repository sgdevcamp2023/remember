package harmony.communityservice.guild.category.service.command;

import harmony.communityservice.guild.category.dto.DeleteCategoryRequest;
import harmony.communityservice.guild.category.dto.ModifyCategoryRequest;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;

public interface CategoryCommandService {

    Long register(RegisterCategoryRequest registerCategoryRequest);

    void delete(DeleteCategoryRequest deleteCategoryRequest);

    void modify(ModifyCategoryRequest modifyCategoryRequest);

    void deleteByGuildId(Long guildId);
}
