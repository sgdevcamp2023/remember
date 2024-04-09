package harmony.communityservice.guild.guild.repository.command.impl;

import harmony.communityservice.guild.guild.repository.command.GuildReadCommandRepository;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildReadCommandRepository;
import harmony.communityservice.guild.domain.GuildRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadCommandRepositoryImpl implements GuildReadCommandRepository {

    private final JpaGuildReadCommandRepository repository;

    @Override
    public void save(GuildRead guildRead) {
        repository.save(guildRead);
    }

    @Override
    public void delete(long guildId) {
        repository.deleteGuildReadsByGuildId(guildId);
    }
}
