package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.GuildReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaGuildReadCommandRepository;
import harmony.communityservice.community.domain.GuildRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadCommandRepositoryImpl implements GuildReadCommandRepository {

    private final JpaGuildReadCommandRepository repository;

    @Override
    public void save(GuildRead guildRead) {
        repository.save(guildRead);
    }
}
