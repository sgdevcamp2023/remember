package harmony.communityservice.guild.category.repository.query;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.domain.GuildId;
import java.util.List;

public interface CategoryQueryRepository {

    List<Category> findListByGuildId(GuildId guildId);
}
