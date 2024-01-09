package harmony.communityservice.community.query.repository.impl;

import harmony.communityservice.community.domain.User;
import harmony.communityservice.community.query.repository.UserQueryRepository;
import harmony.communityservice.community.query.repository.jpa.JpaUserQueryRepository;
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
