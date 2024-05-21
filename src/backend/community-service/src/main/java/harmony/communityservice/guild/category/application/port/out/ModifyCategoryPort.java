package harmony.communityservice.guild.category.application.port.out;

import harmony.communityservice.guild.category.domain.Category.CategoryId;

public interface ModifyCategoryPort {

    void modify(CategoryId categoryId, String newName);
}
