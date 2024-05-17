package harmony.communityservice.guild.category.repository.query;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;
import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findListByGuildId(GuildIdJpaVO guildId);
}
