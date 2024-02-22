package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.domain.GuildUser;
import harmony.communityservice.community.command.repository.GuildUserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildUserCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildUserCommandRepositoryImpl implements GuildUserCommandRepository {

    private final JpaGuildUserCommandRepository repository;

    @Override
    public void save(GuildUser guildUser) {
        repository.save(guildUser);
    }
}
