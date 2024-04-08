package harmony.communityservice.guild.category.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.guild.category.mapper.ToSearchCategoryResponseMapper;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;
import harmony.communityservice.guild.category.repository.query.CategoryQueryRepository;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.domain.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

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
    @AuthorizeGuildMember
    public List<SearchCategoryResponse> searchListByGuildId(SearchParameterMapperRequest searchCategoryListRequest) {
        List<Category> categories = categoryQueryRepository.findCategoriesByGuildId(
                searchCategoryListRequest.guildId());
        return categories.stream()
                .map(ToSearchCategoryResponseMapper::convert)
                .collect(Collectors.toList());
    }
}
