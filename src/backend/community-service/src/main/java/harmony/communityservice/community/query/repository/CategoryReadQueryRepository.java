package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.CategoryRead;
import java.util.List;

public interface CategoryReadQueryRepository {
    List<CategoryRead> findAllByGuildId(Long guildId);
}
