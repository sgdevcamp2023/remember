package harmony.communityservice.community.query.service.impl;

import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.query.repository.CategoryReadQueryRepository;
import harmony.communityservice.community.query.service.CategoryReadQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryReadQueryServiceImpl implements CategoryReadQueryService {
    private final UserReadQueryService userReadQueryService;
    private final CategoryReadQueryRepository categoryReadQueryRepository;

    @Override
    public List<CategoryRead> findCategoryReadsByGuildId(long guildId, long userId) {
        userReadQueryService.existsUserIdAndGuildId(userId, guildId);
        return categoryReadQueryRepository.findAllByGuildId(guildId);
    }
}
