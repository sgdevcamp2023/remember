package harmony.communityservice.user.repository.query.impl;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.repository.query.UserQueryRepository;
import harmony.communityservice.user.repository.query.jpa.JpaUserQueryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserQueryRepositoryImpl implements UserQueryRepository {

    private final JpaUserQueryRepository jpaUserQueryRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return jpaUserQueryRepository.findById(userId);
    }
}
