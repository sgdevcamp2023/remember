package harmony.communityservice.guild.category.controller;

import harmony.communityservice.common.annotation.AuthorizeUser;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthorizeUser
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CategoryQueryController {

    private final CategoryQueryService categoryQueryService;

    @GetMapping("/search/category/{guildId}/{userId}")
    public BaseResponse<?> searchInGuild(@PathVariable Long userId, @PathVariable Long guildId) {
        List<SearchCategoryResponse> searchCategoryResponses = categoryQueryService.searchListByGuildId(
                new SearchParameterMapperRequest(guildId, userId));
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchCategoryResponses);
    }
}
