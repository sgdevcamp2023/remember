package harmony.communityservice.guild.category.application.port.out;

import harmony.communityservice.guild.category.domain.Category;

public interface RegisterCategoryPort {

    void register(Category category);
}
