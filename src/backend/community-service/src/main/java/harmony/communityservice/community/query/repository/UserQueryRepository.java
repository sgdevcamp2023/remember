package harmony.communityservice.community.query.repository;

import harmony.communityservice.community.domain.User;
import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findById(Long userId);
}
