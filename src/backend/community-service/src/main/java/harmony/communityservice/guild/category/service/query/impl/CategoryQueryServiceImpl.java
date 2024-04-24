package harmony.communityservice.guild.category.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;
import harmony.communityservice.guild.category.mapper.ToSearchCategoryResponseMapper;
import harmony.communityservice.guild.category.repository.query.CategoryQueryRepository;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.category.domain.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryQueryRepository categoryQueryRepository;

    @Override
    @AuthorizeGuildMember
    public List<SearchCategoryResponse> searchListByGuildId(SearchParameterMapperRequest searchCategoryListRequest) {
        List<Category> categories = categoryQueryRepository.findListByGuildId(searchCategoryListRequest.guildId());
        return categories.stream()
                .map(category -> ToSearchCategoryResponseMapper.convert(category,
                        searchCategoryListRequest.guildId(), category.getCategoryId()))
                .collect(Collectors.toList());
    }
}
