package harmony.communityservice.guild.guild.repository.command.impl;

import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import harmony.communityservice.guild.domain.Guild;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildCommandRepositoryImpl implements GuildCommandRepository {

    private final JpaGuildCommandRepository repository;

    @Override
    public void save(Guild guild) {
        repository.save(guild);
    }

    @Override
    public void delete(Long guildId) {
        repository.deleteById(guildId);
    }
}
