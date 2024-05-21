package harmony.communityservice.guild.category.adapter.in.web;

import harmony.communityservice.common.annotation.WebAdapter;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.guild.category.application.port.in.LoadCategoriesQuery;
import harmony.communityservice.guild.category.application.port.in.SearchCategoryResponse;
import harmony.communityservice.guild.category.application.port.in.SearchListCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadCategoryController {

    private final LoadCategoriesQuery loadCategoriesQuery;

    @GetMapping("/search/category/{guildId}/{userId}")
    public BaseResponse<?> searchInGuild(@PathVariable Long userId, @PathVariable Long guildId) {
        SearchCategoryResponse searchCategoryResponse = loadCategoriesQuery.loadList(
                new SearchListCommand(guildId, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchCategoryResponse);
    }
}
