package harmony.communityservice.user.repository.command;

import harmony.communityservice.user.domain.User;
import harmony.communityservice.user.domain.UserId;
import java.util.Optional;

public interface UserCommandRepository {

    void save(User user);

    Optional<User> findById(UserId userId);
}
