package harmony.communityservice.guild.category.repository.query;

import harmony.communityservice.guild.category.domain.Category;
import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findListByGuildId(Long guildId);
}
