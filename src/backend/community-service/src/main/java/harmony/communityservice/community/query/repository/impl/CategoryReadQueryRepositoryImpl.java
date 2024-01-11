package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.CategoryRead;
import harmony.communityservice.community.query.repository.CategoryReadQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaCategoryReadQueryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryReadQueryRepositoryImpl implements CategoryReadQueryRepository {

    private final JpaCategoryReadQueryRepository jpaCategoryReadQueryRepository;

    @Override
    public List<CategoryRead> findAllByGuildId(Long guildId) {
        return jpaCategoryReadQueryRepository.findCategoryReadsByGuildId(guildId);
    }
}
