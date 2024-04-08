package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.common.dto.VerifyGuildMemberRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.mapper.ToSearchCategoryResponseMapper;
import harmony.communityservice.community.query.dto.SearchCategoryResponse;
import harmony.communityservice.community.query.repository.CategoryQueryRepository;
import harmony.communityservice.community.query.service.CategoryQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryQueryRepository categoryQueryRepository;
    private final UserReadQueryService userReadQueryService;

    @Override
    public Category searchByCategoryId(Long categoryId) {
        return categoryQueryRepository.findByCategoryId(categoryId).orElseThrow(NotFoundDataException::new);
    }

    @Override
    public void existsByCategoryIdAndGuildId(Long categoryId, long guildId) {
        if (!categoryQueryRepository.existsByCategoryIdAndGuildId(categoryId, guildId)) {
            throw new NotFoundDataException("해당하는 카테고리가 존재하지 않습니다");
        }
    }

    @Override
    public List<SearchCategoryResponse> searchListByGuildId(Long guildId, Long userId) {
        userReadQueryService.existsByUserIdAndGuildId(new VerifyGuildMemberRequest(userId, guildId));
        List<Category> categories = categoryQueryRepository.findCategoriesByGuildId(guildId);
        return categories.stream()
                .map(ToSearchCategoryResponseMapper::convert)
                .collect(Collectors.toList());
    }
}
