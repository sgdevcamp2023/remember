package harmony.communityservice.user.repository.command.impl;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.repository.command.UserCommandRepository;
import harmony.communityservice.user.repository.command.jpa.JpaUserCommandRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {

    private final JpaUserCommandRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public Optional<User> findById(Long userId) {
        return repository.findById(userId);
    }
}
