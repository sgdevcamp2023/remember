package harmony.communityservice.user.repository.command.impl;

import harmony.communityservice.guild.guild.domain.GuildId;
import harmony.communityservice.user.domain.UserId;
import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.repository.command.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.user.domain.UserRead;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadCommandRepositoryImpl implements UserReadCommandRepository {

    private final JpaUserReadCommandRepository repository;

    @Override
    public void save(UserRead userRead) {
        repository.save(userRead);
    }

    @Override
    public Optional<UserRead> findByUserIdAndGuildId(UserId userId, GuildId guildId) {
        return repository.findByUserIdAndGuildId(userId, guildId);
    }

    @Override
    public List<UserRead> findListByUserId(UserId userId) {
        return repository.findUserReadsByUserId(userId);
    }
}
