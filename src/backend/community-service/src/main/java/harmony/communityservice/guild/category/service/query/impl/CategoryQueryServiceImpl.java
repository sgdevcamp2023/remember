package harmony.communityservice.guild.category.service.query.impl;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import harmony.communityservice.guild.category.dto.SearchCategoryResponse;
import harmony.communityservice.guild.category.mapper.ToSearchCategoryResponseMapper;
import harmony.communityservice.guild.category.service.query.CategoryQueryService;
import harmony.communityservice.guild.domain.Category;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.service.query.GuildQueryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final GuildQueryService guildQueryService;

    @Override
    @AuthorizeGuildMember
    public List<SearchCategoryResponse> searchListByGuildId(SearchParameterMapperRequest searchCategoryListRequest) {
        Guild targetGuild = guildQueryService.searchById(searchCategoryListRequest.guildId());
        List<Category> categories = targetGuild.getCategories();
        return categories.stream()
                .map(category -> ToSearchCategoryResponseMapper.convert(category,
                        searchCategoryListRequest.guildId(), categories.indexOf(category)))
                .collect(Collectors.toList());
    }
}
