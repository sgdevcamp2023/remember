package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.domain.User;
import harmony.communityservice.community.command.repository.UserCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserCommandRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCommandRepositoryImpl implements UserCommandRepository {

    private final JpaUserCommandRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }
}
