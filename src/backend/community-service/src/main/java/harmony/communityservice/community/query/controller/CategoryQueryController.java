package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.query.service.CategoryReadQueryService;
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

    private final CategoryReadQueryService categoryReadQueryService;

    @GetMapping("/check/{guildId}/{userId}")
    public BaseResponse<?> findCategories(@PathVariable Long guildId, @PathVariable Long userId) {
        List<CategoryRead> categoryReads = categoryReadQueryService.findCategoryReadsByGuildId(guildId,
                userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", categoryReads);
    }
}
