package harmony.communityservice.user.repository.command.impl;

import harmony.communityservice.user.repository.command.UserReadCommandRepository;
import harmony.communityservice.user.repository.command.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.user.domain.UserRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadCommandRepositoryImpl implements UserReadCommandRepository {

    private final JpaUserReadCommandRepository repository;

    @Override
    public void save(UserRead userRead) {
        repository.save(userRead);
    }
}
