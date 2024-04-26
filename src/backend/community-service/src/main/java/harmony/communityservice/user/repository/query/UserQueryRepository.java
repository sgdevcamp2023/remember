package harmony.communityservice.user.repository.query;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;
import java.util.Optional;

public interface UserQueryRepository {
    Optional<User> findById(UserId userId);
}
