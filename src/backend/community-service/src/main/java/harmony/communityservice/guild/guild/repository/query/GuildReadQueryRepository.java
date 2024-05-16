package harmony.communityservice.guild.guild.repository.query;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.user.adapter.out.persistence.UserId;
import java.util.List;

public interface GuildReadQueryRepository {
    List<GuildRead> findGuildsByUserId(UserId userId);

    List<GuildId> findGuildIdsByUserId(UserId userId);
}
