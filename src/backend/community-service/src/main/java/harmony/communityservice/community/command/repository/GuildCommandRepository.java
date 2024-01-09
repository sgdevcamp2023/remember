package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Guild;

public interface GuildCommandRepository {
    void save(Guild guild);
}
