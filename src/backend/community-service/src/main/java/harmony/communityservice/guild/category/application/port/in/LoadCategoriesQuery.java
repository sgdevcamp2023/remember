package harmony.communityservice.guild.category.application.port.in;

public interface LoadCategoriesQuery {

    LoadCategoryResponse loadList(LoadListCommand searchListCommand);
}
