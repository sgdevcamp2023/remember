package harmony.communityservice.guild.guild.repository.command.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.domain.GuildRead;
import harmony.communityservice.guild.guild.repository.command.GuildReadCommandRepository;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildReadCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildReadCommandRepositoryImpl implements GuildReadCommandRepository {

    private final JpaGuildReadCommandRepository repository;

    @Override
    public void save(GuildRead guildRead) {
        repository.save(guildRead);
    }

    @Override
    public void delete(GuildId guildId) {
        repository.deleteGuildReadsByGuildId(guildId);
    }
}
