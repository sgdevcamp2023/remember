package harmony.communityservice.guild.category.application.port.in;

public interface LoadCategoriesQuery {

    SearchCategoryResponse loadList(SearchListCommand searchListCommand);
}
