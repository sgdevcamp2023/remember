package harmony.communityservice.guild.category.repository.query;

import harmony.communityservice.guild.domain.Category;
import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findListByGuildId(Long guildId);
}
