package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.SearchCategoryResponse;
import harmony.communityservice.community.query.service.CategoryQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CategoryQueryController {

    private final CategoryQueryService categoryQueryService;

    @GetMapping("/search/category/{guildId}/{userId}")
    public BaseResponse<?> searchInGuild(@PathVariable Long guildId, @PathVariable Long userId) {
        List<SearchCategoryResponse> searchCategoryResponses = categoryQueryService.searchListByGuildId(guildId,
                userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchCategoryResponses);
    }
}
