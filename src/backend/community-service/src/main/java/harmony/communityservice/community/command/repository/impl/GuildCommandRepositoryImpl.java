package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.command.repository.GuildCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildCommandRepositoryImpl implements GuildCommandRepository {

    private final JpaGuildCommandRepository repository;

    @Override
    public void save(Guild guild) {
        repository.save(guild);
    }
}
