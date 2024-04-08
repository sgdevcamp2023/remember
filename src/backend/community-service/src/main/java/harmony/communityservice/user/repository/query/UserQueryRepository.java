package harmony.communityservice.user.repository.query;

import harmony.communityservice.user.domain.User;
import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findById(Long userId);
}
