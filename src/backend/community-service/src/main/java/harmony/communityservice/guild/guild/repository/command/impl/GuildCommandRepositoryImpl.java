package harmony.communityservice.guild.guild.repository.command.impl;

import harmony.communityservice.guild.guild.domain.Guild;
import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.guild.guild.repository.command.GuildCommandRepository;
import harmony.communityservice.guild.guild.repository.command.jpa.JpaGuildCommandRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GuildCommandRepositoryImpl implements GuildCommandRepository {

    private final JpaGuildCommandRepository repository;

    @Override
    public void save(Guild guild) {
        repository.save(guild);
    }

    @Override
    public void deleteById(GuildId guildId) {
        repository.deleteById(guildId);
    }

    @Override
    public Optional<Guild> findById(GuildId guildId) {
        return repository.findById(guildId);
    }

    @Override
    public Optional<Guild> findByInvitationCode(String invitationCode) {
        return repository.findByInviteCode(invitationCode);
    }
}
