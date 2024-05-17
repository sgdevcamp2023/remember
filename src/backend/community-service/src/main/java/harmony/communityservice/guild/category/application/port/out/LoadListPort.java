package harmony.communityservice.guild.category.application.port.out;

import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;

public interface LoadListPort {

    List<Category> loadList(GuildId guildId);
}
