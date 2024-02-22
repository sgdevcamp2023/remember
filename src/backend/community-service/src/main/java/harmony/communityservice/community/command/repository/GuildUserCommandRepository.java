package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.GuildUser;

public interface GuildUserCommandRepository {

    void save(GuildUser guildUser);
}
