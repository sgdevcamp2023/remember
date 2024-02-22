package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.UserReadCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaUserReadCommandRepository;
import harmony.communityservice.community.domain.UserRead;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadCommandRepositoryImpl implements UserReadCommandRepository {

    private final JpaUserReadCommandRepository repository;

    @Override
    public void save(UserRead userRead) {
        repository.save(userRead);
    }
}
